const sha256 = require('./sha256');
const fetch = require('node-fetch');
const https = require('https');
const elliptic = require('elliptic');
const forge = require('node-forge');

config = {
    host: 'localhost'
};


function getScannerIDfromQRCode(qrCode) {
    url = new URL(qrCode),
    url_parts = url.pathname.split('/'); 
    scannerId = url_parts[2];
    return scannerId;
}


const agent = new https.Agent({
    rejectUnauthorized: false
  })


function checkIn(route, checkInData) {
    fetch(route, {
        agent: agent,
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: checkInData
    });
  }


function recursiveCheckin(scannerID, num_check_ins) {
    
    console.log('[LUCI APP] Recursive check in' + num_check_ins);
    num_check_ins != 0 ? (num_check_ins--, fetch('https://' + config.host + '/api/v3/scanners/' + scannerID, { agent }).then(scannerData => {
        scannerData.json().then(scannerDataJson => {
            console.log(scannerDataJson);
            var checkInData = JSON.stringify(buildCheckin({
                'publicKey': scannerDataJson['publicKey'],
                'scannerId': scannerDataJson['scannerId']
            }, ge(makeCode())));

            // fetch('https://' + config.host + '/checkin?body=' + checkInData, { agent }).then(_ => {

            console.log(checkInData)
            checkIn('https://' + config.host + '/api/v3/traces/checkin/', checkInData)

            // fetch('https://' + config.host + '/api/v3/traces/checkin/' + checkInData, { agent }).then(_ => {

            //     // updateStatus(num_check_ins + 1, scannerDataJson.name);
            //     // sleep(1400);
            //     recursiveCheckin(scannerID, num_check_ins);
            // });


        });
    }).catch(e => {
        console.log('Luci APP error');
        console.error(e);
    })) : (console.log('...'));
}


function makeCode() {
    chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-:+=^!/*?&<>()[]{}@%$#'
    encoder = {
        'getRanHex': targetLength => {
            let ranHex = [];
            let hexTokens = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'];
            for (let i = 0; i < targetLength; i++) {
                ranHex.push(hexTokens[Math.floor(Math.random() * 16)]);
            }
            return ranHex.join('');
        },
        'parseHexString': stringToParse => {
            var parsedString = [];
            while (stringToParse.length >= 2) {
                parsedString.push(parseInt(stringToParse.substring(0, 2), 16));
                stringToParse = stringToParse.substring(2, stringToParse.length);
            }
            return parsedString;
        }
    };

    let varA = '030101';
    let varB = Math.floor(Date['now']() / 1000).toString(16);

    varB = varB.match(/(..?)/g).reverse().join('');
    varA += varB;
    varA += encoder.getRanHex(178);
    varA += forge.util.bytesToHex(sha256(encoder.parseHexString(varA))).slice(0, 8)
    
    return varA; 
}


function ge(_input) {
    const varA = forge.util.hexToBytes(_input);
    let _buffer = forge.util.createBuffer(varA);
    _buffer.getBytes(2);
    const keyId = _buffer.getByte();
    const timestamp = _buffer.getInt32Le();
    const traceId = forge.util.encode64(_buffer.getBytes(16));
    const data = forge.util.encode64(_buffer.getBytes(32));
    const publicKey = forge.util.encode64(_buffer.getBytes(33));
    const verificationTag = forge.util.encode64(_buffer.getBytes(8));
    return {
        'v': 3,
        'data': data,
        'keyId': keyId,
        'traceId': traceId,
        'publicKey': publicKey,
        'timestamp': timestamp,
        'deviceType': 1,
        'verificationTag': verificationTag
    };
}


function buildCheckin(scannerData, geMakeCode) {
        let varA = '03'
            .concat(forge.util.bytesToHex([geMakeCode.keyId]))
            .concat(forge.util.bytesToHex(forge.util.decode64(scannerData.publicKey)))
            .concat(forge.util.bytesToHex(forge.util.decode64(geMakeCode.verificationTag)));
       
        let varB = encrypt_dlies(forge.util.bytesToHex(forge.util.decode64(scannerData.publicKey)), varA);
       
        let publicKey = varB.publicKey;
        let data = varB.data;
        let iv = varB.iv;
        let mac = varB.mac;
    return {
        'traceId': geMakeCode.traceId,
        'scannerId': scannerData.scannerId,
        'timestamp': geMakeCode.timestamp,
        'data': forge.util.encode64(forge.util.hexToBytes(data)),
        'iv': forge.util.encode64(forge.util.hexToBytes(iv)),
        'mac': forge.util.encode64(forge.util.hexToBytes(mac)),
        'publicKey': forge.util.encode64(forge.util.hexToBytes(publicKey)),
        'deviceType': geMakeCode.deviceType
    };
}

function encrypt_dlies(_input1, _input2) {
    
    var _elliptic = new elliptic.ec('p256');
    let keyPair = _elliptic.genKeyPair();

    let publicKey = keyPair.derive(_elliptic.keyFromPublic(_input1, 'hex').getPublic()).toString('hex');
    let publicKeyHash = forge.util.bytesToHex(sha256(publicKey + '01')).slice(0, 32);
    let publicKeyHash2 = forge.util.bytesToHex(sha256(publicKey + '02'));
    let _randByte = forge.util.bytesToHex(forge.random.getBytesSync(16));
    let aesCtrEncrypted = encrypt_aes_ctr(_input2, publicKeyHash, _randByte);
    let _mac = forge.hmac.create();
    _mac.start('sha256', publicKeyHash2);
    _mac['update'](aesCtrEncrypted);
    _mac = _mac.digest().toHex();
    return {
        'publicKey': keyPair.getPublic('hex'),
        'data': aesCtrEncrypted,
        'iv': _randByte,
        'mac': _mac
    };
}

function encrypt_aes_ctr(_input1, publicKeyHash, _randomByte) {
    var cipher= forge.cipher.createCipher('AES-CTR', forge.util.hexToBytes(publicKeyHash));
    cipher.start({
        'iv': forge.util.hexToBytes(_randomByte)
    });
    cipher.update(forge.util.createBuffer(forge.util.hexToBytes(_input1)));
    cipher.finish(); 
    return cipher.output.toHex();
}



scannerID = getScannerIDfromQRCode('https://localhost/webapp/43496077-6c4c-4a61-b89e-be92288ef091#e30/CWA1/CAESJggBEgpUZXN0YXVyYW50GhZTdHJhw59lIDEsIDEyMzQ1IFN0YWR0GnYIARJggwLMzE153tQwAOf2MZoUXXfzWTdlSpfS99iZffmcmxOG9njSK4RTimFOFwDh6t0Tyw8XR01ugDYjtuKwjjuK49Oh83FWct6XpefPi9Skjxvvz53i9gaMmUEc96pbtoaAGhC4YitWWxHRHw0_EahMGu1gIgYIARAEGHg')
recursiveCheckin(scannerID, 1)
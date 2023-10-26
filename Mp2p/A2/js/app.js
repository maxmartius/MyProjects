const glob_array = ['then', '23329ljJdNM', 'name', '35637yiuPLr', 'reload', 'length', 'error', '1KAjQuv', 'checkin-form', 'venuename', 'start', '[LUCI\x20APP]\x20Using\x20CORS\x20Proxy:\x20', '53EvREXf', 'status', 'catch', '12Daxelm', 'stringify', '[LUCI\x20APP]\x20recursiveCheckin:\x20', 'publicKey', 'innerHTML', 'value', 'none', 'display', 'again', 'pathname', '641487BweIbT', 'log', '614712YfBOVO', '[LUCI\x20APP]\x20updateStatus:\x20', 'hostname', 'block', 'result', 'reader', 'now', 'host', '[LUCI\x20APP]\x20Error!', '[LUCI\x20APP]\x20Scanner\x20ID:\x20', 'environment', '411953gKevss', '509933ZSCFTf', '/scanners?scannerId=', '23afUZDO', 'style', '[LUCI\x20APP]\x20Invalid\x20URL\x20scanned.', 'warn', '6803DjdFzm', 'Vielen\x20Dank\x20f&uuml;rs\x20Mitmachen!', 'getElementById', 'getCameras', '[LUCI\x20APP]\x20QR\x20error\x20=\x20'];
const a0_0x28a906 = FUNK1;
(function(glob_array, _0x1589c8) {
    const _0x31731b = FUNK1;
    while (!![]) {
        try {
            const _0x2891c0 = parseInt(_0x31731b(0xcd)) + parseInt(_0x31731b(0xad)) * -parseInt(_0x31731b(0xcc)) + -parseInt(_0x31731b(0xb5)) * -parseInt(_0x31731b(0xa9)) + -parseInt(_0x31731b(0xc1)) + -parseInt(_0x31731b(0xb2)) * -parseInt(_0x31731b(0xa1)) + parseInt(_0x31731b(0xbf)) + -parseInt(_0x31731b(0x9d)) * parseInt(_0x31731b(0xa7));
            if (_0x2891c0 === _0x1589c8) break;
            else glob_array['push'](glob_array['shift']());
        } catch (_0x16abff) {
            glob_array['push'](glob_array['shift']());
        }
    }
}(glob_array, 0x5be47));//376391

function FUNK1(_0x137f2c, _0x32fd0c) {
    _0x137f2c = _0x137f2c - 0x9d; //157
    let _0x447f26 = glob_array[_0x137f2c];
    return _0x447f26;
}
var config = {};
config[a0_0x28a906(0xc8)] = location[a0_0x28a906(0xc3)];//200

function onScanSuccess(_0x536f1c, _0x46d660, _0x381208) {//url redader eingecheckter
    const glob = a0_0x28a906,
        _0xb60854 = new URL(_0x536f1c),
        _0x4dcf65 = _0xb60854[glob(0xbe)]['split']('/');//pathname
    if (_0x4dcf65[glob(0xab)] < 0x3) return reportError(glob(0x9f));
    _0x46d660['stop']()['then'](_0x406aa7 => {
        const _0x6701f6 = glob;
        scannerId = _0x4dcf65[0x2], console[_0x6701f6(0xc0)](_0x6701f6(0xca) + scannerId), generateFakeVisitFor(scannerId, _0x381208);
    })[glob(0xb4)](_0x466e0c => {});
}

function onScanFailure(_0x129298) {
    const _0x4a6849 = a0_0x28a906;
    console[_0x4a6849(0xa0)](_0x4a6849(0xa5) + _0x129298);
}

function reload() {
    const _0x27c7c3 = a0_0x28a906;
    location[_0x27c7c3(0xaa)]();
}

function sleep(_0x1a442f) {
    const _0x4c00a = a0_0x28a906,
        _0x2ff293 = Date[_0x4c00a(0xc7)]();
    let _0x3b6691 = null;
    do {
        _0x3b6691 = Date[_0x4c00a(0xc7)]();
    } while (_0x3b6691 - _0x2ff293 < _0x1a442f);
}

function recursiveCheckin(_0x25d9c7, _0x356110) {
    const _0x44a037 = a0_0x28a906;
    console['log'](_0x44a037(0xb7) + _0x356110),
        _0x356110 != 0x0 ? (_0x356110--, fetch('https://' + config[_0x44a037(0xc8)] + _0x44a037(0xce) + _0x25d9c7)[_0x44a037(0xa6)](_0x1a6c20 => {
        const _0x487d0a = _0x44a037;
        _0x1a6c20['json']()[_0x487d0a(0xa6)](_0x3b0f15 => {
            const _0x524248 = _0x487d0a;
            console[_0x524248(0xc0)]('[LUCI\x20APP]\x20Location:\x20' + _0x3b0f15[_0x524248(0xa8)]);
            var _0xf42c9c = JSON[_0x524248(0xb6)](buildCheckin({
                'publicKey': _0x3b0f15[_0x524248(0xb8)],
                'scannerId': _0x3b0f15['scannerId']
            }, ge(makeCode())));
            fetch('https://' + config[_0x524248(0xc8)] + '/checkin?body=' + _0xf42c9c)[_0x524248(0xa6)](_0x497e63 => {
                const _0x48904f = _0x524248;
                updateStatus(_0x356110 + 0x1, _0x3b0f15[_0x48904f(0xa8)]), sleep(0x4b0), recursiveCheckin(_0x25d9c7, _0x356110);
            });
        });
    })['catch'](_0x12278b => {
        const _0x44843d = _0x44a037;
        console[_0x44843d(0xc0)](_0x44843d(0xc9)), console[_0x44843d(0xac)](_0x12278b);
    })) : (toggleStatus(), displayCheckinSuccess());
}

function generateFakeVisitFor(_0x3badc6, _0x13227e) {
    const _0x49ee4d = a0_0x28a906;
    console['log'](_0x49ee4d(0xb1) + config[_0x49ee4d(0xc8)]), hideCheckinFormAndReader();
    const _0x563c52 = document[_0x49ee4d(0xa3)](_0x49ee4d(0xc5));
    var _0xaac873 = 0x1;
    switch (_0x13227e) {
        case '1':
            _0xaac873 = 0x10;
            break;
        case '2':
            _0xaac873 = 0x4;
            break;
        case '3':
            _0xaac873 = 0x19;
            break;
        case '4':
            _0xaac873 = 0x1;
            break;
        default:
            _0xaac873 = 0x1;
            break;
    }
    recursiveCheckin(_0x3badc6, _0xaac873), toggleStatus();
}

function hideCheckinFormAndReader() {
    const _0x3f655a = a0_0x28a906,
        _0x4cfedd = document[_0x3f655a(0xa3)](_0x3f655a(0xae)),
        _0x416290 = document[_0x3f655a(0xa3)]('reader');
    _0x416290['style'][_0x3f655a(0xbc)] = 'none', _0x4cfedd[_0x3f655a(0x9e)][_0x3f655a(0xbc)] = _0x3f655a(0xbb);
}

function toggleStatus() {
    const _0x240b1f = a0_0x28a906,
        _0x38064 = document[_0x240b1f(0xa3)](_0x240b1f(0xb3));
    _0x38064[_0x240b1f(0x9e)][_0x240b1f(0xbc)] == '' ? _0x38064['style'][_0x240b1f(0xbc)] = _0x240b1f(0xc4) : _0x38064[_0x240b1f(0x9e)][_0x240b1f(0xbc)] = 'none';
}

function updateStatus(_0x29b593, _0x14f059) {
    const _0x110166 = a0_0x28a906;
    console[_0x110166(0xc0)](_0x110166(0xc2) + _0x29b593 + ',\x20' + _0x14f059);
    const _0x3433d1 = document[_0x110166(0xa3)]('count'),
        _0x20b12a = document[_0x110166(0xa3)](_0x110166(0xaf));
    _0x3433d1[_0x110166(0xb9)] = _0x29b593, _0x20b12a[_0x110166(0xb9)] = _0x14f059;
}

function displayCheckinSuccess() {
    const _0x7078b2 = a0_0x28a906,
        _0x2350f2 = document[_0x7078b2(0xa3)](_0x7078b2(0xc5)),
        _0x321c3c = document['getElementById'](_0x7078b2(0xbd));
    _0x2350f2[_0x7078b2(0xb9)] = _0x7078b2(0xa2), _0x2350f2[_0x7078b2(0x9e)][_0x7078b2(0xbc)] = 'block', _0x321c3c[_0x7078b2(0x9e)][_0x7078b2(0xbc)] = _0x7078b2(0xc4);
}

function init() {
    const glob = a0_0x28a906;
    var _0x4e99dd = document['getElementById']('recipients')["value"];//value
    Html5Qrcode["getCameras"]()[glob(0xa6)](_0x3f57bf => {//164-166 => "1KAjQuv" venuename
        const glob1 = glob;
        if (_0x3f57bf && _0x3f57bf[glob1(0xab)]) {//171=>catch 5
            var _0x34347d = _0x3f57bf[0x0]['id'];
            const _0x35248a = new Html5Qrcode(glob1(0xc6));//198 =>
            _0x35248a[glob1(0xb0)]({//176 => 19 innerhtml
                'facingMode': glob1(0xcb)// 203 => 46 => vielen Dank
            }, {
                'fps': 0xa,
                'qrbox': 0xfa
            }, _0x47fe69 => {
                onScanSuccess(_0x47fe69, _0x35248a, _0x4e99dd);// ? Htmlscan hostname
            }, _0x404dde => {})[glob1(0xb4)](_0x2bfecc => {});
        }
    })['catch'](_0x342b89 => {});
}
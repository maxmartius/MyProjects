const a0_0x53aa = ['getRanHex', 'random', 'match', 'verificationTag', 'push', '40487KupEFu', 'mac', 'hex', 'AES-CTR', 'parseHexString', '29EatYui', '23KHSpQn', 'cipher', 'timestamp', 'getPublic', 'update', 'createCipher', 'hmac', 'finish', 'getBytes', '714672KTfFac', 'keyId', 'sha256', 'length', 'bytesToHex', 'concat', 'scannerId', 'create', '030101', 'hexToBytes', 'toString', 'output', 'traceId', '5zUBMMP', 'substring', 'floor', 'createBuffer', '96253mRbbFN', 'deviceType', '1709068OLWvFn', '7691aZuiZT', '164294wzWKfF', '237996IqTMgQ', 'getInt32Le', 'p256', 'toHex', 'join', 'encode64', '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.-:+=^!/*?&<>()[]{}@%$#', 'data', 'util', 'decode64', 'slice', 'getBytesSync', 'start', 'reverse'];
(function(_0x3b63f2, _0x15aa90) {
    const _0x339a76 = a0_0x9088;
    while (!![]) {
        try {
            const _0x11a163 = -parseInt(_0x339a76(0x141)) + parseInt(_0x339a76(0x140)) * -parseInt(_0x339a76(0x15b)) + parseInt(_0x339a76(0x164)) + -parseInt(_0x339a76(0x15a)) * parseInt(_0x339a76(0x155)) + -parseInt(_0x339a76(0x139)) * parseInt(_0x339a76(0x13d)) + parseInt(_0x339a76(0x142)) + parseInt(_0x339a76(0x13f));
            if (_0x11a163 === _0x15aa90) break;
            else _0x3b63f2['push'](_0x3b63f2['shift']());
        } catch (_0x3aab34) {
            _0x3b63f2['push'](_0x3b63f2['shift']());
        }
    }
}(a0_0x53aa, 0xa2649));

function a0_0x9088(_0x327af8, _0x4fe1f1) {
    _0x327af8 = _0x327af8 - 0x132;
    let _0x53aa12 = a0_0x53aa[_0x327af8];
    return _0x53aa12;
}

function makeCode() {
    const _0x55fd96 = a0_0x9088,
        _0x1b7037 = _0x55fd96(0x148),
        _0x122ddc = {
            'getRanHex': _0x493507 => {
                const _0x560ae2 = _0x55fd96;
                let _0x7dd249 = [],
                    _0x364b8e = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'];
                for (let _0x39f756 = 0x0; _0x39f756 < _0x493507; _0x39f756++) {
                    _0x7dd249[_0x560ae2(0x154)](_0x364b8e[Math[_0x560ae2(0x13b)](Math[_0x560ae2(0x151)]() * 0x10)]);
                }
                return _0x7dd249[_0x560ae2(0x146)]('');
            },
            'encode': _0x36449c => {
                const _0x236790 = _0x55fd96;
                if (_0x36449c[_0x236790(0x167)] % 0x4) return;
                var _0x1bef80 = 0x0,
                    _0x5d1b81 = _0x36449c[_0x236790(0x167)],
                    _0x4cd69d = 0x0,
                    _0x1a68b8, _0x4f3d7e, _0x2649b7 = '';
                while (_0x1bef80 < _0x5d1b81) {
                    _0x4cd69d = _0x4cd69d * 0x100 + _0x36449c[_0x1bef80++];
                    if (_0x1bef80 % 0x4) continue;
                    _0x4f3d7e = 0x55 * 0x55 * 0x55 * 0x55;
                    while (_0x4f3d7e >= 0x1) {
                        _0x1a68b8 = Math['floor'](_0x4cd69d / _0x4f3d7e) % 0x55, _0x2649b7 += _0x1b7037[_0x1a68b8], _0x4f3d7e /= 0x55;
                    }
                    _0x4cd69d = 0x0;
                }
                return _0x2649b7;
            },
            'parseHexString': _0x173ef5 => {
                const _0x314632 = _0x55fd96;
                var _0x3ff680 = [];
                while (_0x173ef5[_0x314632(0x167)] >= 0x2) {
                    _0x3ff680['push'](parseInt(_0x173ef5[_0x314632(0x13a)](0x0, 0x2), 0x10)), _0x173ef5 = _0x173ef5[_0x314632(0x13a)](0x2, _0x173ef5[_0x314632(0x167)]);
                }
                return _0x3ff680;
            }
        };
    let _0x2ccf5e = _0x55fd96(0x134),
        _0x334f1f = Math[_0x55fd96(0x13b)](Date['now']() / 0x3e8)[_0x55fd96(0x136)](0x10);
    return _0x334f1f = _0x334f1f[_0x55fd96(0x152)](/(..?)/g)[_0x55fd96(0x14f)]()[_0x55fd96(0x146)](''), _0x2ccf5e += _0x334f1f, _0x2ccf5e += _0x122ddc[_0x55fd96(0x150)](0xb2), _0x2ccf5e += forge['util'][_0x55fd96(0x168)](sha256(_0x122ddc[_0x55fd96(0x159)](_0x2ccf5e)))[_0x55fd96(0x14c)](0x0, 0x8), _0x2ccf5e;
}

function ge(_0x379d01) {
    const _0x2a8b8b = a0_0x9088,
        _0x2d807c = forge[util][hexToBytes](_0x379d01);
    let _0x27a513 = forge[util][createBuffer](_0x2d807c);
    _0x27a513['getBytes'](0x2);
    const _0x16ce5c = _0x27a513['getByte'](),
        _0x167816 = _0x27a513[getInt32Le](),
        _0x145ad2 = forge[util][encode64](_0x27a513[getBytes](0x10)),
        _0x3889b2 = forge[util]['encode64'](_0x27a513[getBytes](0x20)),
        _0x989b35 = forge[util]['encode64'](_0x27a513[getBytes](0x21)),
        _0x4e0f21 = forge['util'][encode64](_0x27a513[getBytes)](0x8)),
        _0x1d1ebd = forge['util'][bytesToHex](_0x27a513getBytes](0x4)),
        _0x124357 = forge[util][bytesToHex](_0x2d807c)['slice'](0x0, 0x2 * _0x2d807c[length] - 0x8);
    return {
        'v': 0x3,
        'data': _0x3889b2,
        'keyId': _0x16ce5c,
        'traceId': _0x145ad2,
        'publicKey': _0x989b35,
        'timestamp': _0x167816,
        'deviceType': 0x1,
        'verificationTag': _0x4e0f21
    };
}

function buildCheckin(_0x4bbbb1, _0x84afe1) {
    const _0x3da00d = a0_0x9088,
        _0x30c7b1 = '  [_0x3da00d(0x169)](forge[_0x3da00d(0x14a)][_0x3da00d(0x168)]([_0x84afe1[_0x3da00d(0x165)]]))['concat'](forge['util'][_0x3da00d(0x168)](forge[_0x3da00d(0x14a)][_0x3da00d(0x14b)](_0x4bbbb1['publicKey'])))['concat'](forge[_0x3da00d(0x14a)][_0x3da00d(0x168)](forge['util'][_0x3da00d(0x14b)](_0x84afe1[_0x3da00d(0x153)]))),
        _0x809a90 = encrypt_dlies(forge[_0x3da00d(0x14a)][_0x3da00d(0x168)](forge[_0x3da00d(0x14a)][_0x3da00d(0x14b)](_0x4bbbb1['publicKey'])), _0x30c7b1),
        _0xf51e00 = _0x809a90['publicKey'],
        _0x19365e = _0x809a90[_0x3da00d(0x149)],
        _0x47573f = _0x809a90['iv'],
        _0x29c5f7 = _0x809a90[_0x3da00d(0x156)];
    return {
        'traceId': _0x84afe1[_0x3da00d(0x138)],
        'scannerId': _0x4bbbb1[_0x3da00d(0x132)],
        'timestamp': _0x84afe1[_0x3da00d(0x15d)],
        'data': forge[_0x3da00d(0x14a)][_0x3da00d(0x147)](forge[_0x3da00d(0x14a)]['hexToBytes'](_0x19365e)),
        'iv': forge['util']['encode64'](forge[_0x3da00d(0x14a)]['hexToBytes'](_0x47573f)),
        'mac': forge['util']['encode64'](forge[_0x3da00d(0x14a)][_0x3da00d(0x135)](_0x29c5f7)),
        'publicKey': forge[_0x3da00d(0x14a)]['encode64'](forge[_0x3da00d(0x14a)]['hexToBytes'](_0xf51e00)),
        'deviceType': _0x84afe1[_0x3da00d(0x13e)]
    };
}

function encrypt_dlies(_0x334c0c, _0x32af97) {
    const _0x2197b9 = a0_0x9088,
        _0x2677a4 = new elliptic['ec'](_0x2197b9(0x144)),
        _0x5b48eb = _0x2677a4['genKeyPair'](),
        _0x1a590d = _0x5b48eb['derive'](_0x2677a4['keyFromPublic'](_0x334c0c, _0x2197b9(0x157))[_0x2197b9(0x15e)]())[_0x2197b9(0x136)](_0x2197b9(0x157)),
        _0x1d673b = forge[_0x2197b9(0x14a)][_0x2197b9(0x168)](sha256(_0x1a590d + '01'))[_0x2197b9(0x14c)](0x0, 0x20),
        _0x57de2b = forge['util'][_0x2197b9(0x168)](sha256(_0x1a590d + '02')),
        _0x1d4de4 = forge[_0x2197b9(0x14a)][_0x2197b9(0x168)](forge[_0x2197b9(0x151)][_0x2197b9(0x14d)](0x10)),
        _0x5c3159 = encrypt_aes_ctr(_0x32af97, _0x1d673b, _0x1d4de4);
    let _0x30ec22 = forge[_0x2197b9(0x161)][_0x2197b9(0x133)]();
    return _0x30ec22[_0x2197b9(0x14e)](_0x2197b9(0x166), _0x57de2b), _0x30ec22['update'](_0x5c3159), _0x30ec22 = _0x30ec22['digest']()[_0x2197b9(0x145)](), {
        'publicKey': _0x5b48eb['getPublic'](_0x2197b9(0x157)),
        'data': _0x5c3159,
        'iv': _0x1d4de4,
        'mac': _0x30ec22
    };
}

function encrypt_aes_ctr(_0x4de6f3, _0x29c1b5, _0x16f7f6) {
    const _0x5f423a = a0_0x9088;
    var _0x45e942 = forge[_0x5f423a(0x15c)][_0x5f423a(0x160)](_0x5f423a(0x158), forge[_0x5f423a(0x14a)]['hexToBytes'](_0x29c1b5));
    return _0x45e942[_0x5f423a(0x14e)]({
        'iv': forge['util']['hexToBytes'](_0x16f7f6)
    }), _0x45e942[_0x5f423a(0x15f)](forge['util']['createBuffer'](forge[_0x5f423a(0x14a)][_0x5f423a(0x135)](_0x4de6f3))), _0x45e942[_0x5f423a(0x162)](), _0x45e942[_0x5f423a(0x137)][_0x5f423a(0x145)]();
}
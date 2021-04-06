import VueCookies from 'vue-cookie';
// 是否登录过期
function isLogin() {
    if (VueCookies.get('token')) {
        return true
    } else {
        return false
    }
}

//获取user信息
function getUser() {
    let user = localStorage.getItem('user');
    return JSON.parse(user)
}

//时间戳转换
function changeTime(value) {
    let time = new Date(Number(value * 1000));
    let year = time.getFullYear();
    let month = time.getMonth() + 1;
    let day = time.getDate();
    let hour = time.getHours() < 10 ? '0' + time.getHours() : time.getHours();
    let min = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes();
    if (time.toDateString() === new Date().toDateString()) {
        //今天 
        return `${hour}:${min}`
    } else if (year == new Date().getFullYear()) {
        //今年
        return `${month}-${day} ${hour}:${min}`
    } else {
        //其他年
        return `${year}-${month}-${day} ${hour}:${min}`
    }
}
//获取unix格式的时间戳
function getTime() {
    return Math.round(new Date().getTime() / 1000);
}
/**
    * 编码
    * 报文格式：magic4字节 + 版本1字节 + 序列化算法1字节 + 指令1字节 + 数据长度4字节 + 数据内容
    * 总长度 = 11 + 数据内容
    * @param packet 数据包
    * @returns {ArrayBuffer}
**/
function encode(packet) {
    let bytes = stringToBytes(JSON.stringify(packet));
    let buffer = new ArrayBuffer(11 + bytes.length);
    if (buffer.byteLength !== 11 + bytes.length) {
        console.log("编码分配内存失败，内存不足");
        return null;
    }
    let dataView = new DataView(buffer);

    dataView.setInt32(0, 0x12345678);
    dataView.setInt8(4, packet.version);
    dataView.setInt8(5, 1); // 写死1表示json序列化
    dataView.setInt8(6, packet.command);
    dataView.setInt32(7, bytes.length);
    for (let i = 11; i < bytes.length + 11; i++) {
        dataView.setUint8(i, bytes[i - 11]);
    }
    return dataView.buffer;
}
/**
 * 解码
 * 报文格式：magic4字节 + 版本1字节 + 序列化算法1字节 + 指令1字节 + 数据长度4字节 + 数据内容
 * 总长度 = 11 + 数据内容
 * @param buffer {ArrayBuffer}
 * @returns {JSON}
**/
function decode(buffer) {
    let dataView = new DataView(buffer);
    let length = dataView.getInt32(7);
    let bytes = [];
    for (let i = 11; i < length + 11; i++) {
        bytes[i - 11] = dataView.getUint8(i);
    }
    let json = bytesToString(bytes);
    return JSON.parse(json);
}
/**
    * 字符串转byte数组
    * @param str
    * @returns {Array}
 **/
function stringToBytes(str) {
    let bytes = [];
    let len, c;
    len = str.length;
    for (let i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if (c >= 0x010000 && c <= 0x10ffff) {
            bytes.push(((c >> 18) & 0x07) | 0xf0);
            bytes.push(((c >> 12) & 0x3f) | 0x80);
            bytes.push(((c >> 6) & 0x3f) | 0x80);
            bytes.push((c & 0x3f) | 0x80);
        } else if (c >= 0x000800 && c <= 0x00ffff) {
            bytes.push(((c >> 12) & 0x0f) | 0xe0);
            bytes.push(((c >> 6) & 0x3f) | 0x80);
            bytes.push((c & 0x3f) | 0x80);
        } else if (c >= 0x000080 && c <= 0x0007ff) {
            bytes.push(((c >> 6) & 0x1f) | 0xc0);
            bytes.push((c & 0x3f) | 0x80);
        } else {
            bytes.push(c & 0xff);
        }
    }
    return bytes;
}
/**
 * byte数组转字符串
 * @param bytes
 * @returns {string|string|string}
 */
function bytesToString(bytes) {
    if (typeof bytes === 'string') {
        return bytes;
    }
    let str = '', _arr = bytes;
    for (let i = 0; i < _arr.length; i++) {
        let one = _arr[i].toString(2), v = one.match(/^1+?(?=0)/);
        if (v && one.length === 8) {
            let bytesLength = v[0].length;
            let store = _arr[i].toString(2).slice(7 - bytesLength);
            for (let st = 1; st < bytesLength; st++) {
                store += _arr[st + i].toString(2).slice(2);
            }
            str += String.fromCharCode(parseInt(store, 2));
            i += bytesLength - 1;
        } else {
            str += String.fromCharCode(_arr[i]);
        }
    }
    return str;
}

//对象根据属性排序
function sortByAttr(attr) {
    return function (a, b) {
        let val1 = a[attr];
        let val2 = b[attr];
        return val2 - val1;
    }
}
//将数组中的某个元素挪到第一位
function moveAttr(arr, key) {
    arr.unshift(...arr.splice(arr.findIndex(i => i.user.id === key), 1));
}
export default {
    isLogin,
    getUser,
    changeTime,
    getTime,
    encode,
    decode,
    sortByAttr,
    moveAttr
}
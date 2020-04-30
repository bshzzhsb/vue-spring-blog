export function parseTime(time, cFormat, pad=true) {
    if (arguments.length === 0) {
        return null;
    }
    const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}';
    let date;
    if (typeof time === 'object') {
        date = time;
    } else {
        if (typeof time === 'string') {
            if ((/^[0-9]+$/.test(time))) {
                time = parseInt(time);
            }
        }
        if ((typeof time === 'number') && (time.toString().length === 10)) {
            time = time * 1000;
        }
        date = new Date(time);
    }
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay(),
    };
    const time_str = format.replace(/{([ymdhisa])+}/g, (result, key) => {
        const value = formatObj[key];
        if (key === 'a') {
            return ['日', '一', '二', '三', '四', '五', '六'][value]
        }
        if (pad) {
            return value.toString().padStart(2, '0');
        } else {
            return value.toString();
        }
    });
    return time_str
}

export function formatTime(time, option, pad=true) {
    const d = new Date(time);
    const now = Date.now();

    const diff = (now - d)/1000;
    if (diff < 30) {
        return '刚刚';
    } else if (diff < 3600) {
        return Math.ceil(diff / 60) + '分钟前';
    } else if (diff < 3600 * 24) {
        return Math.ceil(diff / 3600) + '小时前';
    } else if (diff < 3600 * 24 * 2) {
        return '一天前'
    }
    if (option) {
        return parseTime(time, option, pad);
    } else {
        return (
            d.getFullYear() + '年' + (d.getMonth()+1) + '月' + d.getDate() + '日' + d.getHours() + '时' + d.getMinutes() + '分'
        );
    }
}
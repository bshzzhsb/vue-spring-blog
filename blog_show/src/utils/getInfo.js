import Cookies from "js-cookie"

const infoKey = "info";

export function getInfo() {
    return Cookies.get(infoKey);
}

export function setInfo(nickname, email, web) {
    const inOneMonth = new Date(new Date().getTime() + 1000*60*60*24*30);
    let info = {nickname: nickname, email: email, web: web};
    return Cookies.set(infoKey, info, {expires: inOneMonth});
}

export function resetInfo() {
    return Cookies.remove(infoKey);
}

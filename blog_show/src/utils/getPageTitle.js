import defaultSettings from '../settings'

const title = defaultSettings.title || '博思何在';

export default function getPageTitle(pageTile) {
    if (pageTile) {
        return `${pageTile}-${title}`
    }
    return `${title}`;
}

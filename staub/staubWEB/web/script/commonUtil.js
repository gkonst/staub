function expandOrCollapseElement(objArgs) {
    if (objArgs == null || typeof objArgs != 'object') {
        return;
    }
    if (typeof objArgs['elementId'] == 'undefined') {
        return;
    }
    var elementId = objArgs['elementId'];
    var className = typeof objArgs['className'] == 'undefined' ? 'expanded' : objArgs['className'];
    var expandedLabel = typeof objArgs['expandedLabel'] == 'undefined' ? 'Expand' : objArgs['expandedLabel'];
    var collapsedLabel = typeof objArgs['collapsedLabel'] == 'undefined' ? 'Collapse' : objArgs['collapsedLabel'];
    var switcher = document.getElementById(elementId + 'Switcher');
    if (switchClassName(elementId, className)) {
        switcher.innerHTML = collapsedLabel;
    } else {
        switcher.innerHTML = expandedLabel;
    }
}
function switchClassName(elementId, className) {
    var element = document.getElementById(elementId);
    if (isClassNameOnElement(element, className)) {
        removeClassName(element, className);
        return false;
    } else {
        addClassName(element, className);
        return true;
    }
}

function isClassNameOnElement(element, className) {
    return element.className.indexOf(className) != -1;
}

function addClassName(element, className) {
    if (!isClassNameOnElement(element, className)) {
        element.className += ' ' + className;
    }
}

function removeClassName(element, className) {
    if (isClassNameOnElement(element, className)) {
        element.className = element.className.replace(className, '');
    }
}
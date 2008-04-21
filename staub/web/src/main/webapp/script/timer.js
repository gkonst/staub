if (typeof Widget == 'undefined') {
    var Widget = {};
    Widget.all = [];
}
if (typeof Widget.Timer == 'undefined') {
    Widget.Timer = {};
}
Widget.Timer = function(objArgs) {
    this.timeToCount = objArgs.timeToCount;
    this.timerFieldId = objArgs.timerFieldId;
    this.onExpire = objArgs.onExpire;
    if (typeof objArgs.timerId == 'undefined') {
        this.id = Widget.all.length;
    } else {
        this.id = objArgs.timerId;
    }
    if (Widget.getWidgetById(this.id) != null) {
        Widget.updateWidgetById(this.id, this);
    }
    Widget.all.push(this);
    Widget.Timer.tickTock(this.id);
};
Widget.Timer.prototype.destroy = function() {
    if (typeof this.t != 'undefined') {
        clearTimeout(this.t);
    }
}
Widget.Timer.destroyById = function(id) {
    var timer = Widget.getWidgetById(id);
    if (timer != null && typeof timer.t != 'undefined') {
        clearTimeout(timer.t);
    }
}
Widget.Timer.tickTock = function(id) {
    var timer = Widget.getWidgetById(id);
    var timerField = document.getElementById(timer.timerFieldId);
    var sec = timer.timeToCount % 60;
    var min = (timer.timeToCount - sec) / 60;
    if (sec < 10) {
        sec = '0' + sec;
    }
    timerField.innerHTML = min + ':' + sec;
    timer.timeToCount--;
    if (timer.timeToCount > -1) {
        timer.t = setTimeout("Widget.Timer.tickTock('" + id + "')", 1000);
    } else {
        timer.destroy();
        if (timer.onExpire) {
            timer.onExpire();
        }
    }
}
Widget.getWidgetById = function(id) {
    for (var i = 0; i < Widget.all.length; i++) {
        if (Widget.all[i].id == id) {
            return Widget.all[i];
        }
    }
    return null;
}
Widget.updateWidgetById = function(id, newWidget) {
    for (var i = 0; i < Widget.all.length; i++) {
        if (Widget.all[i].id == id) {
            Widget.all[i].destroy();
            Widget.all.splice(i,1, newWidget);
            return;
        }
    }
}
if (typeof Timer == 'undefined') {
  Timer = {};
}
Timer.init = function(objArgs) {
    Timer.destroy();
    Timer.timeToCount = objArgs.timeToCount;
    Timer.currentTime = objArgs.currentTime;
    Timer.timerFieldId = objArgs.timerFieldId;
    Timer.tickTock();
};
Timer.destroy = function() {
    if(typeof Timer.t != 'undefined') {
        clearTimeout(Timer.t);
    }
}
Timer.tickTock = function() {
        
        var timerField = document.getElementById(Timer.timerFieldId);
        var sec = Timer.currentTime % 60;
        var min = (Timer.currentTime - sec) / 60;
        if (sec < 10) {
            sec = '0' + sec;
        }
        timerField.innerHTML = min + ':' + sec;
        Timer.currentTime--;
    if (Timer.currentTime != -1) {
        Timer.t = setTimeout('Timer.tickTock()', 1000);
    } else {
        nextQuestion();
    }
}
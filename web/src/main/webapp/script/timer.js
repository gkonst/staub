if (typeof Timer == 'undefined') {
  Timer = {};
}
Timer = function(objArgs) {
    this.timeToCount = objArgs.timeToCount;
    this.currentTime = objArgs.currentTime;
    this.timerFieldId = objArgs.timerFieldId;
    Timer.tickTock(this.timerFieldId, this.currentTime);
};
Timer.tickTock = function(timerFieldId, currentTime) {
        var timerField = document.getElementById(timerFieldId);
        var sec = currentTime % 60;
        var min = (currentTime - sec) / 60;
        if (sec < 10) {
            sec = '0' + sec;
        }
        timerField.innerHTML = min + ':' + sec;
        currentTime--;
    if (currentTime != -1) {
        setTimeout('Timer.tickTock(\'' + timerFieldId + '\', ' + currentTime + ')', 1000);
    } else {
        nextQuestion();
    }
}
AJS.$(document).ready(function(){
    var CREATE_SWITCH_ID = '#createSwitch';
    var TOGGLE_ON_ID = '#toggleOn';
    var TOGGLE_OFF_ID = '#toggleOff';

    function createSwitch(e) {
        e.preventDefault();
        console.log(JIRA.Issue);
    }

    function toggleOn(e) {
        e.preventDefault();
        console.log(JIRA);
    }

    function toggleOff(e) {
        e.preventDefault();
        console.log(JIRA.Issue.getIssueKey());
    }

    AJS.$(CREATE_SWITCH_ID).click(createSwitch);
    AJS.$(TOGGLE_ON_ID).click(toggleOn);
    AJS.$(TOGGLE_OFF_ID).click(toggleOff);


})
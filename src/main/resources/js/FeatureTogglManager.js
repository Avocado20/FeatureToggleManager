AJS.$(document).ready(function(){
    var CREATE_SWITCH_ID = "#createSwitch";
    var TOGGLE_ON_ID = "#toggleOn";
    var TOGGLE_OFF_ID = "#toggleOff";
    var SERVLET_URL = "featuretoggleservlet";

    function createSwitch(e) {
        e.preventDefault();
        createSwitchRequest(JIRA.Issue.getIssueKey(), getCurrentIssueSummary());
    }

    function toggleOn(e) {
        e.preventDefault();
        switchToggleRequest(true);
    }

    function toggleOff(e) {
        e.preventDefault();
        switchToggleRequest(false);
    }

    function switchToggleRequest(shouldBeTurnedOn) {
        AJS.$.ajax({
            url : getFeatureServletUrl(),
            method: "get",
            contentType : "application/json",
            dataType : "json",
            async: false,
            data: {
                method: "switchToggle",
                key: AJS.$("#key-val").text(),
                turnOn: shouldBeTurnedOn,
            }
        })
        .done(function(res) {
            console.log(res);
            var translation = res.isEnabled == true ? AJS.I18n.getText('feature.toggle.enabled') : AJS.I18n.getText("feature.toggle.disabled")
            JIRA.Messages.showSuccessMsg(translation);
        })
        .fail(function(res) {
            console.log(res.responseText);
            JIRA.Messages.showErrorMsg(res.responseText);
        })
    }

    function createSwitchRequest(jiraIssueKey, issueDescription) {
        AJS.$.ajax({
            url : getFeatureServletUrl(),
            method: "get",
            contentType : "application/json",
            dataType : "json",
            async: false,
            data: {
                method: "createSwitch",
                key: jiraIssueKey,
                description: issueDescription,
                isEnabled: false,
            }
        })
        .done(function(res) {
            JIRA.Messages.showSuccessMsg(AJS.I18n.getText("feature.toggle.created"));
        })
        .fail(function() {})
    }

    function getFeatureServletUrl() {
        return AJS.params.baseURL + "/plugins/servlet/" + SERVLET_URL;
    }

    function getCurrentIssueSummary() {
        return AJS.$("#summary-val").text();
    };

    AJS.$(CREATE_SWITCH_ID).click(createSwitch);
    AJS.$(TOGGLE_ON_ID).click(toggleOn);
    AJS.$(TOGGLE_OFF_ID).click(toggleOff);

})
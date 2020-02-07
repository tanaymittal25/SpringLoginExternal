package com.howtodoinjava.demo.resource;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.HtmlUtils;

@Controller
@SessionAttributes("authorizationRequest")
public class AuthConfirm {
@RequestMapping("/oauth/confirm_access")
public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
final String approvalContent = createTemplate(model, request);
if (request.getAttribute("_csrf") != null) {
model.put("_csrf", request.getAttribute("_csrf"));
}
View approvalView = new View() {
@Override
public String getContentType() {
return "text/html";
}

@Override
public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
response.setContentType(getContentType());
response.getWriter().append(approvalContent);
}
};
return new ModelAndView(approvalView, model);
}

protected String createTemplate(Map<String, Object> model, HttpServletRequest request) {
AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
String clientId = authorizationRequest.getClientId();

StringBuilder builder = new StringBuilder();
String imageLocation = "/home/administrator/Downloads/ui/html/volob-consent/img/citizen_bank_logo.png";
builder.append("<html lang=\"en\" class=\"bank\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");

builder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
" <meta name=\"author\" content=\"WSO2\">");
builder.append("<title>Citizen Bank Open Banking Solution</title>");

builder.append(" <link rel=\"stylesheet\" href=\"/css/bootstrap.min.css\" id=\"bootstrap\">\n" +
" <link rel=\"stylesheet\" href=\"/css/font-wso2.min.css\">\n" +
" <link href=\"/css/css\" rel=\"stylesheet\">\n" +
" <link rel=\"stylesheet\" href=\"/css/citizenbank.css\" id=\"theme\">");

builder.append("<script type=\"text/javascript\" src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js\"></script>");
builder.append("<script type=\"text/javascript\">");

builder.append("function submitform() {\n" +
" var consent_challenge = location.search.split('consent_challenge=')[1];\n" +
" console.log(consent_challenge);\n" +
"\n" +
" var toRet = new Array();\n" +
" if(document.forms[\"consentform\"].acc.length > 1) {\n" +
" document.forms[\"consentform\"].acc.forEach(function(item, index){\n" +
" if(item.checked) {\n" +
" toRet.push(item.value);\n" +
" } \n" +
" });\n" +
" } else {\n" +
" toRet.push(document.forms[\"consentform\"].acc.value);\n" +
" }\n" +
" document.forms[\"consentform\"].accounts.value = toRet.join(\",\");\n" +
" document.forms[\"consentform\"].action = \"/vol-consent/rest/consent/aispsubmit?consent_challenge=\" + consent_challenge;\n" +
" document.forms[\"consentform\"].submit();\n" +
" }\n" +
"\n" +
" function rejectform() {\n" +
" var consent_challenge = location.search.split('consent_challenge=')[1];\n" +
" console.log(consent_challenge);\n" +
" document.forms[\"consentform\"].action = \"/vol-consent/rest/consent/reject?consent_challenge=\" + consent_challenge;\n" +
" document.forms[\"consentform\"].submit();\n" +
" }\n" +
" \n" +
" Date.prototype.addDays = function(days) {\n" +
" var date = new Date(this.valueOf());\n" +
" date.setDate(date.getDate() + days);\n" +
" return date;\n" +
" }\n" +
" function onPageLoad() {\n" +
"\n" +
" var date = new Date();\n" +
" var userid = location.search.split('userid=')[1];\n" +
" // httpobj.open(\"GET\",\"volob-resource-bi/rest/all-accounts?UserId=\" + userid,true);\n" +
" // httpobj.setRequestHeader(\"Accept\", \"application/json\");\n" +
" // httpobj.send();\n" +
"\n" +
" $('#expiry').text('Please note this permission is valid till : ' + date.addDays(90));\n" +
" \n" +
" $.getJSON(\"/vol-consent/rest/consent/getuseraccounts?UserId=\"+userid, function (data) {\n" +
" $.each(data.Data.Account, function (key, entry) { \n" +
" $('#accounts').append('<div class=\"form-check\"><input type=\"checkbox\" value=\"' + entry.Account[0].Identification + '\" name=\"acc\"><label>' + entry.Account[0].Identification + '<span class=\"text-muted\">- ' + entry.AccountType + ' - ' + entry.AccountSubType + '</span></label></div>')\n" +
" })\n" +
" });\n" +
" }");

builder.append("</script>");
builder.append("</head>");

builder.append("<body onLoad=\"javascript:onPageLoad();\">");
builder.append("<div class=\"page-content-wrapper\">\n" +
" <div class=\"container\">\n" +
"\n" +
" <div class=\"login-form-wrapper\">\n" +
" <div class=\"row\">\n" +
" <div class=\"col-md-12\">\n" +
" <div class=\"brand-container add-margin-bottom-5x\">\n" +
" <div class=\"row\">\n" +
" <div class=\"col-md-12 text-center\">\n" +
" <img src=\"/images/citizen_bank_logo.png\" class=\"logo\">\n" +
" </div>\n" +
" </div>\n" +
" </div>\n" +
" </div>\n" +
" </div>");
builder.append("<div class=\"row\">\n" +
" <div class=\"col-md-12 login\">\n" +
" <div class=\"data-container\">\n" +
" <form class=\"form-horizontal\" method=\"POST\" id=\"consentform\" autocomplete=\"off\">\n" +
" <input type=\"hidden\" name=\"accounts\">\n" +
" <h3><strong>Aggregator</strong> is requesting access to your bank account.</h3>\n" +
" <div class=\"form-group\">\n" +
" <div class=\"col-md-12\">\n" +
" <div class=\"card card-body bg-light\">\n" +
" <label class=\"control-label\">Select account(s):</label>\n" +
" <div id=\"accounts\"></div> \n" +
" \n" +
" </div>\n" +
" </div>\n" +
" </div>\n" +
" <div class=\"form-group\">\n" +
" <div class=\"col-md-12\">\n" +
" <p id=\"expiry\"></p>\n" +
" </div>\n" +
" </div>\n" +
" \n" +
" \n" +
" </div>\n" +
" <div class=\"form-group\">\n" +
" <div class=\"col-md-12\">\n" +
" <p>See permission details below</p>\n" +
" </div>\n" +
" </div>\n" +
" <div class=\"form-group\">\n" +
" <div class=\"col-md-12\">\n" +
" <ul class=\"scope\">\n" +
" <li>ReadAccountsDetail : Allow access to additional elements in the account payload.</li>\n" +
" <li>ReadTransactionsCredits : Allow access only to read credit transactions information.\n" +
" </li>\n" +
" <li>ReadAccountsBasic : Allow access to read basic account information.</li>\n" +
" <li>ReadDirectDebits : Allow access to read all direct debit information</li>\n" +
" </ul>\n" +
" </div>\n" +
" </div>\n");

/*builder.append("<div class=\"form-group\">\n" +
" <div class=\"col-md-12\">\n" +
" <input class=\"btn btn-primary\" type=\"button\" onclick=\"javascript:submitform();\" value=\"Allow\"> \n" +
" <input class=\"btn btn-secondary\" type=\"button\" onclick=\"javascript:rejectform();\" value=\"Deny\"> \n" +
" </div>\n" +
" </div>\n");*/
builder.append("</form>\n" +
" </div>\n" +
" </div>\n" +
" </div>\n" +
" </div>\n" +
" \n");
/*builder.append("<footer class=\"footer\">\n" +
" <div class=\"container-fluid\">\n" +
" <p><span class=\"bank-name\">Citizen Bank</span> | © <script>document.write(new Date().getFullYear());</script> Powered by <a title=\"WSO2\" href=\"http://wso2.com/\" target=\"_blank\"><i class=\"icon fw fw-wso2\"></i></a> Open Banking</p>\n" +
" </div>\n" +
" </footer>\n"); */

builder.append("</div>\n" +
" </div>");

builder.append("<script src=\"/js/jquery-3.3.1.min.js.download\"></script>\n" +
" <script src=\"/js/popper.min.js.download\"></script>\n" +
" <script src=\"/js/bootstrap.min.js.download\"></script>\n" +
" <script src=\"/js/demos.js.download\"></script>");

builder.append("</body");

//builder.append("<body><h1>OAuth Approval</h1>");
builder.append("<p> </p>");
//builder.append(HtmlUtils.htmlEscape(clientId));
//builder.append("\" to access your protected resources?</p>");
builder.append("<form id=\"confirmationForm\" name=\"confirmationForm\" action=\"");

String requestPath = ServletUriComponentsBuilder.fromContextPath(request).build().getPath();
if (requestPath == null) {
requestPath = "";
}

builder.append(requestPath).append("/oauth/authorize\" method=\"post\">");
builder.append("<input name=\"user_oauth_approval\" value=\"true\" type=\"hidden\"/>");

String csrfTemplate = null;
CsrfToken csrfToken = (CsrfToken) (model.containsKey("_csrf") ? model.get("_csrf") : request.getAttribute("_csrf"));
if (csrfToken != null) {
csrfTemplate = "<input type=\"hidden\" name=\"" + HtmlUtils.htmlEscape(csrfToken.getParameterName()) +
"\" value=\"" + HtmlUtils.htmlEscape(csrfToken.getToken()) + "\" />";
}
if (csrfTemplate != null) {
builder.append(csrfTemplate);
}

String authorizeInputTemplate = "<label><input name=\"authorize\" value=\"Authorize\" type=\"submit\"/></label></form>";

if (model.containsKey("scopes") || request.getAttribute("scopes") != null) {
builder.append(createScopes(model, request));//APPROVE and DENY BUTTONS with TEMPLATE
builder.append(authorizeInputTemplate);//AUTHORIZE BUTTON
} else {
builder.append(authorizeInputTemplate);
builder.append("<form id=\"denialForm\" name=\"denialForm\" action=\"");
builder.append(requestPath).append("/oauth/authorize\" method=\"post\">");
builder.append("<input name=\"user_oauth_approval\" value=\"false\" type=\"hidden\"/>");
if (csrfTemplate != null) {
builder.append(csrfTemplate);
}
builder.append("<label><input name=\"deny\" value=\"Deny\" type=\"submit\"/></label></form>");
}
builder.append("<footer class=\"footer\">\n" +
" <div class=\"container-fluid\">\n" +
" <p><span class=\"bank-name\">Citizen Bank</span> | © <script>document.write(new Date().getFullYear());</script> Powered by <a title=\"WSO2\" href=\"http://wso2.com/\" target=\"_blank\"><i class=\"icon fw fw-wso2\"></i></a> Open Banking</p>\n" +
" </div>\n" +
" </footer>\n");
builder.append("</body></html>");

return builder.toString();
}

private CharSequence createScopes(Map<String, Object> model, HttpServletRequest request) {
StringBuilder builder = new StringBuilder("<ul>");
@SuppressWarnings("unchecked")
Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ?
model.get("scopes") : request.getAttribute("scopes"));
for (String scope : scopes.keySet()) {
String approved = "true".equals(scopes.get(scope)) ? " checked" : "";
String denied = !"true".equals(scopes.get(scope)) ? " checked" : "";
scope = HtmlUtils.htmlEscape(scope);


builder.append("<div class=\"form-group\">\n" +
" <div class=\"col-md-12\">\n" +
" <input class=\"btn btn-primary\" type=\"button\" onclick=\"builder.append(approved);\" value=\"Allow\"> \n" +
" <input class=\"btn btn-secondary\" type=\"button\" onclick=\"javascript:rejectform();\" value=\"Deny\"> \n" +
" </div>\n" +
" </div>\n");




builder.append("<div class=\"form-group\">");
//builder.append(scope); //PRINT THE SCOPE BEFORE THE RADIO BUTTONS
builder.append("<input type=\"radio\" name=\"");
builder.append(scope); //GIVES THE ACCESS CODE IN THE URL ONLY (APPROVAL PAGE)
builder.append("\" value=\"true\"").append(approved).append(">Approve</input> ");
builder.append("<input type=\"radio\" name=\"").append(scope).append("\" value=\"false\"");
builder.append(denied).append(">Deny</input></div>");
}
builder.append("</ul>");
return builder.toString();
}
}
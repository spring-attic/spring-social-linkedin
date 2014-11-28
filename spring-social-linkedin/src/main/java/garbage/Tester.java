package garbage;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.linkedin.api.CompanyShare;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;


public class Tester {
    static String apiKey = "75kdr9fwzbbxca";
    static String secretKey = "rAq1sgJGI3J4LzIf";
//    static String accessToken = "AQVUIIxAbPaIQLPaqCAn2bUfrWHbhJ2BP6qVhtALsBNbELvRwnUEK8ZVYoQI85dsRDtOmwuC7HYPFMUhR7vQfaJuV5nFNyeyMQw01QVjXiOWH9eKAeo8E9kRDU66OPP5IXW7BIJTgjaCWuKpCMx3F28HAQIvqPUCcYMTq5KbrdKyGw9Q-T4";
    static String accessToken = "AQXlG_X-i7JyNSqOfQMB2VOvfWda6Wg1QGPUmhVdGVZx8_COQmqi5mMXNG0ExCxyQwZ2UDFIJvcTz_66Czm_0aDnDYg8q7MoaqG0PhNAw3MsNA7cqcLNFIpikUOskYRwNGe9gOMWMUuZDwf3JSUmlGB08AkPo9SgWBWCoFERS8-w9nwCutY";
    static String providerUserId = "VjxP5xvD96";
    static String providerId = "linkedin"; // could this be apiKey?

    public static void main(String[] args) {



//        LinkedInConnectionFactory linkedInConnectionFactory = new LinkedInConnectionFactory(apiKey, secretKey);
//        AccessGrant accessGrant = new AccessGrant(accessToken);
//        Connection<LinkedIn> connection = linkedInConnectionFactory.createConnection(accessGrant);
//
//        Integer groupId = 6819088;
//        String title = "the title";
//        String summary = "the summary";
//        String submittedUrl = "http://google.ca";
//        String submittedImageUrl = "http://dog-breeds.findthebest.com/sites/default/files/465/media/images/Pug_916451.jpg";
        //URI uri = connection.getApi().groupOperations().createPost(groupId, title, summary, submittedUrl, submittedImageUrl);

//        URI uri = connection.getApi().groupOperations().createPost(groupId, title, summary);

//        UserProfile userProfile = connection.fetchUserProfile();
//        System.out.println("user: " + userProfile.getFirstName() + " " + userProfile.getLastName() + " " + userProfile.getEmail());

//      authorize();

        viewProfile();

        //postToGroupSimple();

//        postToGroupNew();

//        createCompanyShare();
    }

    public static void createCompanyShare() {
        int companyId = 2414183;

        LinkedInConnectionFactory linkedInConnectionFactory = new LinkedInConnectionFactory(apiKey, secretKey);
        AccessGrant accessGrant = new AccessGrant(accessToken);

        //ConnectionData(String providerId, String providerUserId, String displayName, String profileUrl, String imageUrl, String accessToken, String secret, String refreshToken, Long expireTime)
        ConnectionData connectionData = new ConnectionData(providerId, providerUserId, null, null, null, accessToken, secretKey, null, null);

        Connection<LinkedIn> connection = linkedInConnectionFactory.createConnection(connectionData);

        CompanyShare.Content content  = new CompanyShare.Content("the content title", "http://google.ca","http://www.funpic.hu/funblog/pugs/pugs_17.jpg", "a great description should go here!");

        // targeting
		// -- share target by geography for North America
        List<String> tValuesAsStringsGeo = new ArrayList<String>();
		tValuesAsStringsGeo.add("na"); // North America, see https://developer.linkedin.com/documents/targeting-company-shares#geos
		CompanyShare.TValues tValuesGeo = new CompanyShare.TValues(tValuesAsStringsGeo);
		CompanyShare.ShareTarget shareTargetGeo = new CompanyShare.ShareTarget("geos", tValuesGeo);

        // -- share target by industry
        List<String> tValuesAsStrings = new ArrayList<String>();
        tValuesAsStrings.add("4"); // computer software see https://developer.linkedin.com/documents/industry-codes
        CompanyShare.TValues tValues = new CompanyShare.TValues(tValuesAsStrings);
        CompanyShare.ShareTarget shareTargetIndustry = new CompanyShare.ShareTarget("industries", tValues);

        // create share targets with multiple targets (by geography and industry)
        List<CompanyShare.ShareTarget> shareTargets = new ArrayList<CompanyShare.ShareTarget>();
		shareTargets.add(shareTargetGeo);
        shareTargets.add(shareTargetIndustry);
		CompanyShare.ShareTargetReach shareTargetReach = new CompanyShare.ShareTargetReach(shareTargets);

        CompanyShare share = new CompanyShare(CompanyShare.Visibility.anyone(),
                "trying targeting with multiple targets (by geography and industry) " + System.nanoTime(), content, shareTargetReach);

        URI uri = connection.getApi().companyOperations().createShare(companyId, share);
        System.out.println("URI: " + uri.toString());

    }

    public static void viewProfile() {
        LinkedInConnectionFactory linkedInConnectionFactory = new LinkedInConnectionFactory(apiKey, secretKey);
//        AccessGrant accessGrant = new AccessGrant(accessToken);
//        Connection<LinkedIn> connection = linkedInConnectionFactory.createConnection(accessGrant);
        //ConnectionData(String providerId, String providerUserId, String displayName, String profileUrl, String imageUrl, String accessToken, String secret, String refreshToken, Long expireTime)
        ConnectionData connectionData = new ConnectionData(providerId, providerUserId, null, null, null, accessToken, secretKey, null, null);

        Connection<LinkedIn> connection = linkedInConnectionFactory.createConnection(connectionData);
        UserProfile userProfile = connection.fetchUserProfile();
        System.out.println("user: " + userProfile.getFirstName() + " " + userProfile.getLastName() + " " + userProfile.getEmail());
    }

//    public static void postToGroupSimple() {
//        LinkedInConnectionFactory linkedInConnectionFactory = new LinkedInConnectionFactory(apiKey, secretKey);
//        AccessGrant accessGrant = new AccessGrant(accessToken);
//        Connection<LinkedIn> connection = linkedInConnectionFactory.createConnection(accessGrant);
//                Integer groupId = 6819088;
//        String title = "the title";
//        String summary = "the summary";
//
//        URI uri = connection.getApi().groupOperations().createPost(groupId, title, summary);
//        System.out.println("uri: " + uri.toString());
//    }
//
//    public static void postToGroupNew() {
//        LinkedInConnectionFactory linkedInConnectionFactory = new LinkedInConnectionFactory(apiKey, secretKey);
//        AccessGrant accessGrant = new AccessGrant(accessToken);
//        Connection<LinkedIn> connection = linkedInConnectionFactory.createConnection(accessGrant);
//        Integer groupId = 6819088;
//        String postTitle = "my new title from tester 2";
//        String postSummary = "the new summary from tester 2 - this post should include a submitted URL and a submitted image URL";
//        String contentTitle = "the wonderful content title 2";
//        String submittedUrl = "http://google.ca";
//        String submittedImageUrl = "http://dog-breeds.findthebest.com/sites/default/files/465/media/images/Pug_916451.jpg";
//        String contentDescription = "This is the content description; how nice! 2 ";
//
//        URI uri = connection.getApi().groupOperations().createPost(groupId, postTitle, postSummary, contentTitle,
//                submittedUrl, submittedImageUrl, contentDescription);
//
//        System.out.println("uri: " + uri);
//
//    }

    public static void authorize() {

        Scanner in = new Scanner(System.in);
        String redirectUri = "https://hootsuite.com/"; //needs to be the URI used on registration
        // connection factory
        LinkedInConnectionFactory linkedInConnectionFactory = new LinkedInConnectionFactory(apiKey, secretKey);
        OAuth2Operations oAuth2Operations = linkedInConnectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("https://hootsuite.com/");
        params.setState("thestate");
        // get authorization URL
        String authorizeUrl = oAuth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
        System.out.println("authorize URL: \n" + authorizeUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");
        String code = in.nextLine();
        System.out.println("code: " + code + "\tredirectUri: " + redirectUri);
        //get access grant
        AccessGrant accessGrant = oAuth2Operations.exchangeForAccess(code, redirectUri, null);

        System.out.println("accessGrant=> accessToken: " + accessGrant.getAccessToken() +
                "; refreshToken:" + accessGrant.getRefreshToken() + "; scope: " + accessGrant.getScope() +
                "; expireTime" + accessGrant.getExpireTime());
    }
}

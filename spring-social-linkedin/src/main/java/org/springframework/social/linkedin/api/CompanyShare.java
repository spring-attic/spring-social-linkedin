package org.springframework.social.linkedin.api;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.Assert;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("share")
public class CompanyShare {

    private Visibility visibility;
    private String comment;
    private Content content;
    @XStreamAlias("share-target-reach")
    private ShareTargetReach shareTargetReach;

    public CompanyShare(Visibility visibility, String comment) {
        if(visibility == null) {
            throw new IllegalArgumentException("CompanyShare's visibility cannot be null");
        }
        if (comment == null) {
            comment = "";
        }
        this.visibility = visibility;
        this.comment = comment;
    }

    public CompanyShare(Visibility visibility, String comment, Content content) {
        this(visibility, comment);
        this.content = content;
    }

    public CompanyShare(Visibility visibility, String comment, ShareTargetReach shareTargetReach) {
        this(visibility, comment);
        this.shareTargetReach = shareTargetReach;
    }

    public CompanyShare(Visibility visibility, String comment, Content content, ShareTargetReach shareTargetReach) {
        this(visibility, comment, content);
        this.shareTargetReach = shareTargetReach;
    }

    public Visibility getVisibility() { return visibility; }
    public String getComment() { return comment; }
    public Content getContent() { return content; }
    public ShareTargetReach getShareTargetReach() { return shareTargetReach; }

    public static final class Visibility {
        private static final String ANYONE = "anyone";
        private static final String CONNECTIONS_ONLY = "connections-only";

        private String code;

        private Visibility(String code) {
            this.code = code;
        }

        public static Visibility anyone() {
            return new Visibility(ANYONE);
        }

        public static Visibility connectionsOnly() {
            return new Visibility(CONNECTIONS_ONLY);
        }

        public String getCode() {
            return code;
        }
    }

    public static final class Content implements Serializable {

        private static final long serialVersionUID = 1L;

        private String title;
        @XStreamAlias("submitted-url")
        private String submittedUrl;
        @XStreamAlias("submitted-image-url")
        private String submittedImageUrl;
        private String description;

        public Content(String title, String submittedUrl) {
            Assert.notNull(submittedUrl, "CompanyShare.Content's submittedUrl cannot be null");
            if(submittedUrl.isEmpty()) {
                throw new IllegalArgumentException("CompanyShare.Content's submittedUrl cannot be empty");
            }
            if(title == null) {
                title = ""; //must be specified but can be empty
            }
            this.title = title;
            this.submittedUrl = submittedUrl;
        }

        public Content(String title, String submittedUrl, String submittedImageUrl) {
            this(title, submittedUrl);
            this.submittedImageUrl = submittedImageUrl;
        }

        public Content(String title, String submittedUrl, String submittedImageUrl, String description) {
            this(title, submittedUrl, submittedImageUrl);
            this.description = description;
        }

        public String getSubmittedUrl() { return submittedUrl; }
        public String getSubmittedImageUrl() { return submittedImageUrl; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
    }

    @XStreamAlias("share-target-reach")
    public static final class ShareTargetReach implements Serializable {
        @XStreamAlias("share-targets")
        private List<ShareTarget> shareTargets;

        public ShareTargetReach(List<ShareTarget> shareTargets) {
            this.shareTargets = shareTargets;
        }

        public List<ShareTarget> getShareTargets() { return shareTargets; }
    }

    @XStreamAlias("share-target")
    public static final class ShareTarget implements Serializable {
        private String code;
        @XStreamAlias("tvalues")
        private TValues tValues;

        public ShareTarget(String code, TValues tValues) {
            this.code = code;
            this.tValues = tValues;
        }

        public String getCode() { return code; }
        public TValues gettValues() { return tValues; }
    }

    public static class TValues {
        @XStreamImplicit(itemFieldName="tvalue")
        private List<String> values;

        public TValues(List<String> values) {
            this.values = values;
        }

        public List<String> getValues() { return values; }
    }
}

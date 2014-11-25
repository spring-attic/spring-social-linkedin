package org.springframework.social.linkedin.api;

import java.io.Serializable;

import org.springframework.util.Assert;

public class GroupPost implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title; //cannot be null or empty
    private String summary;//cannot be null but can be empty
    private Content content;

    public GroupPost(String title, String summary) {
        Assert.notNull(title, "GroupPost's title cannot be null");
        if(title.isEmpty()){
            throw new IllegalArgumentException("GroupPost's title cannot be empty");
        }
        if(summary == null) {
            summary = ""; // must be specified, but can be empty
        }
        this.title = title;
        this.summary = summary;
    }

    public GroupPost(String title, String summary, Content content) {
        this(title, summary);
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public Content getContent() { return content; }

    public static final class Content implements Serializable {

        private static final long serialVersionUID = 1L;

        private String title; //cannot be null but can be empty
        private String submittedUrl; //cannot be null, nor empty
        private String submittedImageUrl; //can be null or empty
        private String description; //can be null or empty

        public Content(String title, String submittedUrl) {
            Assert.notNull(submittedUrl, "GroupPost.Content's submittedUrl cannot be null");
            if(submittedUrl.isEmpty()) {
                throw new IllegalArgumentException("GroupPost.Content's submittedUrl cannot be empty");
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
}

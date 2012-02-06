/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.linkedin.api.impl.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.social.linkedin.api.ApiStandardProfileRequest;
import org.springframework.social.linkedin.api.CodeAndName;
import org.springframework.social.linkedin.api.Comment;
import org.springframework.social.linkedin.api.Comments;
import org.springframework.social.linkedin.api.Companies;
import org.springframework.social.linkedin.api.Company;
import org.springframework.social.linkedin.api.Company.CompanyAddress;
import org.springframework.social.linkedin.api.Company.CompanyContactInfo;
import org.springframework.social.linkedin.api.Company.CompanyLocation;
import org.springframework.social.linkedin.api.CompanyJobUpdate;
import org.springframework.social.linkedin.api.CurrentShare;
import org.springframework.social.linkedin.api.Education;
import org.springframework.social.linkedin.api.Group;
import org.springframework.social.linkedin.api.Group.GroupCount;
import org.springframework.social.linkedin.api.Group.GroupPosts;
import org.springframework.social.linkedin.api.Group.GroupRelation;
import org.springframework.social.linkedin.api.GroupMemberships;
import org.springframework.social.linkedin.api.GroupSettings;
import org.springframework.social.linkedin.api.GroupSuggestions;
import org.springframework.social.linkedin.api.ImAccount;
import org.springframework.social.linkedin.api.Job;
import org.springframework.social.linkedin.api.JobBookmark;
import org.springframework.social.linkedin.api.JobBookmarks;
import org.springframework.social.linkedin.api.JobPosition;
import org.springframework.social.linkedin.api.Jobs;
import org.springframework.social.linkedin.api.Likes;
import org.springframework.social.linkedin.api.LinkedInConnections;
import org.springframework.social.linkedin.api.LinkedInDate;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdate;
import org.springframework.social.linkedin.api.LinkedInNetworkUpdates;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.social.linkedin.api.LinkedInProfiles;
import org.springframework.social.linkedin.api.Location;
import org.springframework.social.linkedin.api.MemberGroup;
import org.springframework.social.linkedin.api.NetworkStatistics;
import org.springframework.social.linkedin.api.PersonActivity;
import org.springframework.social.linkedin.api.PhoneNumber;
import org.springframework.social.linkedin.api.Position;
import org.springframework.social.linkedin.api.Post;
import org.springframework.social.linkedin.api.Post.Attachment;
import org.springframework.social.linkedin.api.Post.PostRelation;
import org.springframework.social.linkedin.api.PostComment;
import org.springframework.social.linkedin.api.PostComments;
import org.springframework.social.linkedin.api.Product;
import org.springframework.social.linkedin.api.Product.ProductRecommendation;
import org.springframework.social.linkedin.api.Products;
import org.springframework.social.linkedin.api.Recommendation;
import org.springframework.social.linkedin.api.Relation;
import org.springframework.social.linkedin.api.Share;
import org.springframework.social.linkedin.api.TwitterAccount;
import org.springframework.social.linkedin.api.UpdateAction;
import org.springframework.social.linkedin.api.UpdateContent;
import org.springframework.social.linkedin.api.UpdateContentCompany;
import org.springframework.social.linkedin.api.UpdateContentConnection;
import org.springframework.social.linkedin.api.UpdateContentFollow;
import org.springframework.social.linkedin.api.UpdateContentGroup;
import org.springframework.social.linkedin.api.UpdateContentPersonActivity;
import org.springframework.social.linkedin.api.UpdateContentRecommendation;
import org.springframework.social.linkedin.api.UpdateContentShare;
import org.springframework.social.linkedin.api.UpdateContentStatus;
import org.springframework.social.linkedin.api.UpdateContentViral;
import org.springframework.social.linkedin.api.UrlResource;

/**
 * Jackson module for registering mixin annotations against LinkedIn model classes.
 */
public class LinkedInModule extends SimpleModule {

	public LinkedInModule() {
		super("LinkedInModule", new Version(1, 0, 0, null));
	}
	
	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(LinkedInConnections.class, LinkedInConnectionsMixin.class);
		context.setMixInAnnotations(LinkedInProfile.class, LinkedInProfileMixin.class);
		context.setMixInAnnotations(LinkedInProfileFull.class, LinkedInProfileFullMixin.class);
		context.setMixInAnnotations(MemberGroup.class, MemberGroupMixin.class);
		context.setMixInAnnotations(Recommendation.class, RecommendationMixin.class);
		context.setMixInAnnotations(PersonActivity.class, PersonActivityMixin.class);
		context.setMixInAnnotations(Job.class, JobMixin.class);
		context.setMixInAnnotations(JobPosition.class, JobPositionMixin.class);
		context.setMixInAnnotations(JobBookmark.class, JobBookmarkMixin.class);
		context.setMixInAnnotations(JobBookmarks.class, JobBookmarksMixin.class);
		context.setMixInAnnotations(Company.class, CompanyMixin.class);
		context.setMixInAnnotations(CompanyLocation.class, CompanyLocationMixin.class);
		context.setMixInAnnotations(CompanyAddress.class, CompanyAddressMixin.class);
		context.setMixInAnnotations(CompanyContactInfo.class, CompanyContactInfoMixin.class);
		context.setMixInAnnotations(CompanyJobUpdate.class, CompanyJobUpdateMixin.class);
		context.setMixInAnnotations(CodeAndName.class, CodeAndNameMixin.class);
		context.setMixInAnnotations(UpdateAction.class, UpdateActionMixin.class);
		context.setMixInAnnotations(CurrentShare.class, CurrentShareMixin.class);
		context.setMixInAnnotations(Share.class, ShareMixin.class);
		context.setMixInAnnotations(Share.ShareContent.class, ShareContentMixin.class);
		context.setMixInAnnotations(Share.ShareSource.class, ShareSourceMixin.class);
		context.setMixInAnnotations(Comment.class, CommentMixin.class);
		context.setMixInAnnotations(Comments.class, CommentsMixin.class);
		context.setMixInAnnotations(Likes.class, LikesMixin.class);
		context.setMixInAnnotations(Position.class, PositionMixin.class);
		context.setMixInAnnotations(ImAccount.class, ImAccountMixin.class);
		context.setMixInAnnotations(TwitterAccount.class, TwitterAccountMixin.class);
		context.setMixInAnnotations(UrlResource.class, UrlResourceMixin.class);
		context.setMixInAnnotations(PhoneNumber.class, PhoneNumberMixin.class);
		context.setMixInAnnotations(Education.class, EducationMixin.class);
		context.setMixInAnnotations(Location.class, LocationMixin.class);
		context.setMixInAnnotations(LinkedInDate.class, LinkedInDateMixin.class);
		context.setMixInAnnotations(Relation.class, RelationMixin.class);
		context.setMixInAnnotations(NetworkStatistics.class, NetworkStatisticsMixin.class);
		context.setMixInAnnotations(Companies.class, CompaniesMixin.class);
		context.setMixInAnnotations(LinkedInProfiles.class, LinkedInProfilesMixin.class);
		context.setMixInAnnotations(Jobs.class, JobsMixin.class);
		context.setMixInAnnotations(Product.class, ProductMixin.class);
		context.setMixInAnnotations(ProductRecommendation.class, ProductRecommendationMixin.class);
		context.setMixInAnnotations(Products.class, ProductsMixin.class);
		context.setMixInAnnotations(ApiStandardProfileRequest.class, ApiStandardProfileRequestMixin.class);		
		context.setMixInAnnotations(LinkedInNetworkUpdate.class, LinkedInNetworkUpdateMixin.class);
		context.setMixInAnnotations(LinkedInNetworkUpdates.class, LinkedInNetworkUpdatesMixin.class);
		context.setMixInAnnotations(UpdateContent.class, UpdateContentMixin.class);
		context.setMixInAnnotations(UpdateContentConnection.class, UpdateContentConnectionMixin.class);
		context.setMixInAnnotations(UpdateContentStatus.class, UpdateContentStatusMixin.class);
		context.setMixInAnnotations(UpdateContentGroup.class, UpdateContentGroupMixin.class);
		context.setMixInAnnotations(UpdateContentRecommendation.class, UpdateContentRecommendationMixin.class);
		context.setMixInAnnotations(UpdateContentPersonActivity.class, UpdateContentPersonActivityMixin.class);
		context.setMixInAnnotations(UpdateContentFollow.class, UpdateContentFollowMixin.class);
		context.setMixInAnnotations(UpdateContentViral.class, UpdateContentViralMixin.class);
		context.setMixInAnnotations(UpdateContentShare.class, UpdateContentShareMixin.class);
		context.setMixInAnnotations(UpdateContentCompany.class, UpdateContentCompanyMixin.class);
		context.setMixInAnnotations(Group.class, GroupMixin.class);
		context.setMixInAnnotations(GroupCount.class, GroupCountMixin.class);
		context.setMixInAnnotations(GroupPosts.class, GroupPostsMixin.class);
		context.setMixInAnnotations(GroupRelation.class, GroupRelationMixin.class);
		context.setMixInAnnotations(Post.class, PostMixin.class);
		context.setMixInAnnotations(PostRelation.class, PostRelationMixin.class);
		context.setMixInAnnotations(Attachment.class, AttachmentMixin.class);
		context.setMixInAnnotations(PostComments.class, PostCommentsMixin.class);
		context.setMixInAnnotations(PostComment.class, PostCommentMixin.class);
		context.setMixInAnnotations(GroupSuggestions.class, GroupSuggestionsMixin.class);
		context.setMixInAnnotations(GroupMemberships.class, GroupMembershipsMixin.class);
		context.setMixInAnnotations(GroupSettings.class, GroupSettingsMixin.class);
	}

}

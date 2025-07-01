export class AuditPostInfo {
  constructor(postId,postTitle,categoryName,publishTime,postContent,authorId,authorName) {
    this.postId=postId
    this.postTitle=postTitle
    this.categoryName=categoryName
    this.publishTime=publishTime
    this.postContent=postContent
    this.authorId=authorId
    this.authorName=authorName
  }
}

export class UserComplainInfo {
  constructor(reportId, reportReason, reporterId, reporterName, reportedUserId, reportedUserName, reportTime) {
    this.reportId=reportId
    this.reportReason=reportReason
    this.reporterId=reporterId
    this.reporterName=reporterName
    this.reportedUserId=reportedUserId
    this.reportedUserName=reportedUserName
    this.reportTime=reportTime
  }
}

export class PostComplainInfo {
  constructor(reportId, reportReason, reporterId, reporterName, reportedPostId, postTitle, reportTime) {
    this.reportId=reportId
    this.reportReason=reportReason
    this.reporterId=reporterId
    this.reporterName=reporterName
    this.reportedPostId=reportedPostId
    this.postTitle=postTitle
    this.reportTime=reportTime
  }
}

export class FeedbackInfo {
  constructor(feedbackId, feedbackContent, feedbackUserId, feedbackUserName, feedbackTime){
    this.feedbackId=feedbackId
    this.feedbackContent=feedbackContent
    this.feedbackUserId=feedbackUserId
    this.feedbackUserName=feedbackUserName
    this.feedbackTime=feedbackTime
  }
}

export class CategoryAddInfo {
  constructor(categoryName, description, image){
    this.categoryName=categoryName
    this.description=description
    this.image=image
  }
}
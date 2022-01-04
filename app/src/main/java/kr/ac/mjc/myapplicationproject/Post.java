package kr.ac.mjc.myapplicationproject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class Post implements Parcelable {

    @DocumentId
    private String documentId;

    private String title = "";
    private String message = "";
    private String imageUrl;
    private String writerId;
    private Date uploadDate;

    public Post() {
        uploadDate = new Date();
    }

    protected Post(Parcel in) {
        documentId = in.readString();
        title = in.readString();
        message = in.readString();
        imageUrl = in.readString();
        writerId = in.readString();
        uploadDate = (Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(documentId);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(imageUrl);
        dest.writeString(writerId);
        dest.writeSerializable(uploadDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}

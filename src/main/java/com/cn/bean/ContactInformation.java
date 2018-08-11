package com.cn.bean;

/**
 *  联系方式  与 User 类 一对一关系
 */
public class ContactInformation {

    private int id;

    private String tel;

    private String email;

    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ContactInormation{" +
                "id='" + id + '\'' +
                "tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", user=" + user +
                '}';
    }
}

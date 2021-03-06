package com.iwen.web.qingliao.push.bean.db;

import com.iwen.web.qingliao.push.bean.api.message.MessageCreateModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 消息实体类
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/02/04 16:06
 */
@Entity
@Table(name = "TB_MESSAGE")
public class Message {
    // 发送给人的
    public static final int RECEIVER_TYPE_NONE = 1;
    // 发送给群的
    public static final int RECEIVER_TYPE_GROUP = 2;
    // 字符串
    public static final int TYPE_STR = 1;
    // 图片
    public static final int TYPE_PIC = 2;
    // 文件
    public static final int TYPE_FILE = 3;
    // 语音
    public static final int TYPE_AUDIO = 4;

    /**
     * id
     */
    @Id
    @PrimaryKeyJoinColumn
    // 这里不自动生成UUID，Id由代码写入，由客户端负责生成，避免复杂的映射关系
    // @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;

    /**
     * 内容不允许为空，类型为text
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 附件
     */
    @Column
    private String attach;

    /**
     * 消息类型
     */
    @Column(nullable = false)
    private int type;

    /**
     * 发送者
     * 多个消息对应一个发送者
     */
    @JoinColumn(name = "senderId")
    @ManyToOne(optional = false)
    private User sender;

    // 这个字段只是为了对应sender的数据库字段senderId，不允许手动更新或插入
    @Column(nullable = false, updatable = false, insertable = false)
    private String senderId;

    /**
     * 接收者，可以为空
     * 多个消息对应一个接收者
     */
    @JoinColumn(name = "receiverId")
    @ManyToOne
    private User receiver;

    // 这个字段只是为了对应receiver的数据库字段receiverId，不允许手动更新或插入
    @Column(updatable = false, insertable = false)
    private String receiverId;

    /**
     * 一个群可以接收多条信息
     */
    @JoinColumn(name = "groupId")
    @ManyToOne
    private Group group;
    @Column(updatable = false, insertable = false)
    private String groupId;

    /**
     * 定义为创建时间戳，在创建时就已经写入
     */
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    /**
     * 定义为更新时间戳
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    public Message() {
    }

    /**
     * 普通朋友的发送的构造函数
     *
     * @param sender   发送者
     * @param receiver 接受者
     * @param model    消息模型
     */
    public Message(User sender, User receiver, MessageCreateModel model) {
        this.id = model.getId();
        this.content = model.getContent();
        this.attach = model.getAttach();
        this.type = model.getType();

        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * 发送给群的构造函数
     *
     * @param sender 发送者
     * @param group  群
     * @param model  消息模型
     */
    public Message(User sender, Group group, MessageCreateModel model) {
        this.id = model.getId();
        this.content = model.getContent();
        this.attach = model.getAttach();
        this.type = model.getType();

        this.sender = sender;
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

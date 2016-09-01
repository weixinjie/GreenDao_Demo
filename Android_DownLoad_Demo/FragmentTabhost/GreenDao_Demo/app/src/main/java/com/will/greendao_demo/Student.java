package com.will.greendao_demo;

import org.greenrobot.greendao.annotation.*;

import com.will.greendao_demo.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
/**
 * Entity mapped to table "STUDENT".
 */
@Entity(active = true)
public class Student {

    @Id
    private Long studentId;

    @NotNull
    private String name;
    private int age;
    private long teacherId;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient StudentDao myDao;

    @ToOne(joinProperty = "teacherId")
    private Teacher teacher;

    @Generated
    private transient Long teacher__resolvedKey;

    @Generated
    public Student() {
    }

    public Student(Long studentId) {
        this.studentId = studentId;
    }

    @Generated
    public Student(Long studentId, String name, int age, long teacherId) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.teacherId = teacherId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStudentDao() : null;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @NotNull
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Teacher getTeacher() {
        long __key = this.teacherId;
        if (teacher__resolvedKey == null || !teacher__resolvedKey.equals(__key)) {
            __throwIfDetached();
            TeacherDao targetDao = daoSession.getTeacherDao();
            Teacher teacherNew = targetDao.load(__key);
            synchronized (this) {
                teacher = teacherNew;
            	teacher__resolvedKey = __key;
            }
        }
        return teacher;
    }

    @Generated
    public void setTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new DaoException("To-one property 'teacherId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.teacher = teacher;
            teacherId = teacher.getTeacherId();
            teacher__resolvedKey = teacherId;
        }
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

}

package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

/**
 * 本项目是用来生成DaoManster等类的
 */
public class MyClass {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "com.will.greendao_demo");
        addNote(schema);

        DaoGenerator daoGenerator = new DaoGenerator();
        daoGenerator.generateAll(schema, "./app/src/main/java");
    }

    private static void addNote(Schema schema) {
        Entity teacher = schema.addEntity("Teacher");
        teacher.addLongProperty("teacherId").primaryKey();
        teacher.addStringProperty("name").notNull();

        Entity student = schema.addEntity("Student");
        student.addLongProperty("studentId").primaryKey();
        student.addStringProperty("name").notNull();
        student.addIntProperty("age").notNull();

        //建立一对多关联(教师为1,学生为多)
        Property property = student.addLongProperty("teacherId").notNull().getProperty();
        student.addToOne(teacher, property);
        teacher.addToMany(student, property);
    }
}

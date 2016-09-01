package com.will.greendao_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import org.greenrobot.greendao.query.QueryBuilder;

public class MainActivity extends AppCompatActivity {

    StudentDao studentDao;
    TeacherDao teacherDao;
    QueryBuilder<Student> studentQueryBuilder;
    QueryBuilder<Teacher> teacherQueryBuilder;

    Button add;
    Button delete_all;
    Button delete_one;
    Button delete_teacher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (Button) findViewById(R.id.add);
        delete_all = (Button) findViewById(R.id.delete_all);
        delete_one = (Button) findViewById(R.id.delete_one);
        delete_teacher = (Button) findViewById(R.id.delete_teacher);

        DaoSession daoSession = ((BaseApp) getApplication()).getDaoSession();
        studentDao = daoSession.getStudentDao();
        teacherDao = daoSession.getTeacherDao();

        studentQueryBuilder = studentDao.queryBuilder();
        teacherQueryBuilder = teacherDao.queryBuilder();


        /**
         * 增加一个老师与两个学生
         */
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long id = System.currentTimeMillis();
                Student weixinjie_student = new Student(id, "韦新杰", 20, id + 2);
                Student zhagnrui_student = new Student(id + 1, "张睿", 21, id + 2);
                studentDao.insert(weixinjie_student);
                studentDao.insert(zhagnrui_student);

                Teacher teacher = new Teacher(id + 2, "张光");
                teacherDao.insert(teacher);
                print_message();

            }
        });

        /**
         * 删除所有学生(不包含老师)
         */
        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentDao.deleteAll();
                print_message();
            }
        });

        /**
         * 删除学生表中第一条数据
         */
        delete_one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Student zhagnrui_student = studentQueryBuilder.list().get(0);
                studentDao.deleteByKey(zhagnrui_student.getStudentId());
                print_message();
            }
        });

        /**
         * 删除老师表中第一条数据
         */
        delete_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teacher teacher = teacherQueryBuilder.list().get(0);
                teacherDao.delete(teacher);
                print_message();
            }
        });
    }

    /**
     * 打印表的内容
     */
    private void print_message() {
        if (studentQueryBuilder.list().size() == 0) {
            Log.e("------", "学生是空的");
        } else
            for (Student student : studentQueryBuilder.list()) {
                Log.e("学生-----姓名:", student.getName() + "---年龄:" + student.getAge() + "--ID:" + student.getStudentId());
            }

        if (teacherQueryBuilder.list().size() == 0) {
            Log.e("-----", "老师是空的");
        } else
            for (Teacher teacher : teacherQueryBuilder.list()) {
                Log.e("老师-----姓名:", teacher.getName() + "---Id:" + teacher.getTeacherId());
                for (Student student : teacher.getStudentList()) {
                    Log.e("学生--属于" + teacher.getName(), "---姓名:" + student.getName() + "---年龄:" + student.getAge() + "--ID:" + student.getStudentId());
                }
            }
    }
}

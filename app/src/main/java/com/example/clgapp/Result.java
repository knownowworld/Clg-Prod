package com.example.clgapp;

public class Result {

    String subject;
    Long marks;

      public Result(String s,Long m)
      {
            subject = s;
            marks = m;
      }


      public String retSub()
      {
          return subject;
      }

      public String retMarks()
      {
          return String.valueOf(marks);
      }

}

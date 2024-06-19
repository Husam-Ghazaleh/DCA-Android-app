package uwm.dca;

import java.util.ArrayList;

/**
 * Created by SamGHazaleh on 9/10/16.
 */
public class ActivityModel {

      int type;
      String description;
      String value;
      String date;
      String time;

      static ArrayList<ActivityModel> modelList  = new ArrayList<ActivityModel>();


    public ActivityModel(){

    }


      public ActivityModel(int type){

            this.type = type;

        }

      public ActivityModel ( int type, String description, String datetime, String value){


          this.type = type;
          this.description = description;
          this.value = value;
          this.date = datetime;
      }

      public static ArrayList<ActivityModel> addModeltoList(int type) {

             modelList.add(new ActivityModel(type));

          return modelList;
    }

      int getType(){
         return this.type;
      }

      void setDescription(String description){
         this.description = description;
      }

      void setValue(String value){
         this.value = value;
      }

      void setDate(String date){

          this.date = date;
      }

      void setTime(String time){

        this.time = time;
    }

      String getDescription(){
        return this.description;
      }

      String getValue(){
          return this.value;
      }

      String getDate(){

          return this.date;
      }

      String getTime(){

          return this.time;
      }

      static  ArrayList<ActivityModel> getList(){
         return modelList;
      }

      static void  setElementList(int position,ActivityModel model ) {
          modelList.set(position, model);
          //System.out.println("hhhhh" + modelList.get(position).getDescription());
          //System.out.println("pos" + position);
      }


}

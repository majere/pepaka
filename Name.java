package sample;

class Name {
    static String nameBuilder(String first_name, String last_name){
        String name;
        if(last_name == null){
            name = first_name;
        }else{
            name = first_name + " " +last_name;
        }
        return name;
    }
}

package model;

public class DataSet {
    private Data data[];
    private int index = 0;

    static class Data{
        private String nameColumn;
        private Object valueColumn;

        Data(String nameColumn, Object valueColumn ){
            this.nameColumn = nameColumn;
            this.valueColumn = valueColumn;
        }

        public String getNameColumn(){
            return nameColumn;
        }

        public Object getValueColumn(){
            return valueColumn;
        }
    }

    public DataSet(){
        data = new Data[100];

    }

    public void put(String nameColumn, Object valueColumn){
        data[index++] = new Data(nameColumn, valueColumn);
    }

    public String[] getColumnsNames(){
        String result[] = new String[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getNameColumn();
        }
        return result;
    }

    public Object[] getColumnsValues(){
        Object result[] = new Object[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getValueColumn();
        }
        return result;
    }

    @Override
    public String toString(){
        String []columnsNames = getColumnsNames();
        Object []columnsValues = getColumnsValues();
        String result = "model.DataSet:";
        for (int i = 0; i < index; i++) {
            result += columnsNames[i] + " = " + columnsValues[i] + ", ";
        }
        result = result.substring(0, result.length() - 2) + "\n";
        return result;
    }

}

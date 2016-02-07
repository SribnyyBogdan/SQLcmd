package model;

public class DataSet {
    private Data data[];
    private int freeIndex = 0;

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
        for (int index = 0; index < freeIndex; index++) {
            if (data[index].nameColumn.equals(nameColumn)) {
                data[index].valueColumn = valueColumn;
                return;
            }
        }
        data[freeIndex++] = new Data(nameColumn, valueColumn);
    }

    public String[] getColumnsNames(){
        String result[] = new String[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getNameColumn();
        }
        return result;
    }

    public Object[] getColumnsValues(){
        Object result[] = new Object[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getValueColumn();
        }
        return result;
    }

    public Object get(String name) {
        for (int i = 0; i < freeIndex; i++) {
            if (data[i].getNameColumn().equals(name))
                return data[i].getValueColumn();
        }
        return null;
    }

    public void updateFrom(DataSet dataSet) {
        for (int index = 0; index < dataSet.freeIndex; index++) {
            Data data = dataSet.data[index];
            put(data.nameColumn, data.valueColumn);
        }
    }

    @Override
    public String toString(){
        String []columnsNames = getColumnsNames();
        Object []columnsValues = getColumnsValues();
        String result = "model.DataSet:";
        for (int i = 0; i < freeIndex; i++) {
            result += columnsNames[i] + " = " + columnsValues[i] + ", ";
        }
        result = result.substring(0, result.length() - 2) + "\n";
        return result;
    }

}

package com.noreasonexception.datanuke.app.ValueFilter;


import javax.naming.OperationNotSupportedException;

public class CsvValueFilter implements ValueFilterable<Double> {
    /****
     * Get the order in csv file of the submitted class provided by parameter @param classObj
     * @param classObj the class object
     * @return the id , this id will be used as index in csv array of values
     */
    private int getIdByClassObject(Class<?>classObj){
        return 0;
    }

    /****
     * Gets the context of csv file and translate it into Double[]
     * @implSpec if you change something , then saveCSVContext must be called
     * @return
     */
    protected Double[] getCSVContext(){return null;}

    /****
     * set the context of @param values into file
     * @implNote WARNING! setCSVContext is not saves actually the data , only affects the corresponding member
     * you must manually call .applyCSVContext() or call .saveCSVContext()
     * (a simple wrapper over setCSVContext()+applyCSVContext)
     *
     * @return this(fluent Interface)
     */
    protected CsvValueFilter  setCSVContext(Double [] values){return this;}

    /****
     * Apply the Values set by setCSVContext into actual file
     * @return true on success, false otherwise
     */
    protected boolean  applyCSVContext(){return false;}

    /****
     * a simple wrapper over setCSVContext()+applyCSVContext()
     * @param values
     * @return
     */
    protected boolean  saveCSVContext(Double [] values){
        return setCSVContext(values).applyCSVContext();
    }


    /****
     * .submitValue()
     * @param classObj the class submitted the value
     * @param value the actual value
     * @return
     */
    @Override
    public boolean submitValue(Class<?> classObj, Double value) {
        Double [] tmp=getCSVContext();
        int id;
        if(tmp[id=getIdByClassObject(classObj)].compareTo(value)!=0){
            tmp[id]=value;
            saveCSVContext(tmp);
            return true;
        }
        return false;
    }
}

package data.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 */
@XmlRootElement(name = "datasets")
public class DataListWrapper {

    private List<DataSet> datasets;

    @XmlElement(name = "dataset")
    public List<DataSet> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DataSet> persons) {
        this.datasets = persons;
    }
}

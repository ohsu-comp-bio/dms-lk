package org.labkey.beataml;

import org.labkey.api.data.AbstractTableInfo;
import org.labkey.api.data.ColumnInfo;
import org.labkey.api.data.TableInfo;
import org.labkey.api.data.WrappedColumn;
import org.labkey.api.ldk.table.AbstractTableCustomizer;
import org.labkey.api.query.QueryForeignKey;
import org.labkey.api.query.UserSchema;
import org.labkey.api.study.DatasetTable;

/**
 * Created by bimber on 9/25/2015.
 */
public class DefaultBeatAMLCustomizer extends AbstractTableCustomizer
{
    @Override
    public void customize(TableInfo ti)
    {
        if (ti instanceof AbstractTableInfo)
        {
            if (matches(ti, "study", "patient"))
            {
                customizePatients((AbstractTableInfo) ti);
            }
        }
    }

    private void customizePatients(AbstractTableInfo ti)
    {
        ColumnInfo col = getWrappedIdCol(ti.getUserSchema(), ti, "dataSummary", "demographicsDataSummary");
        col.setLabel("Data Summary");
        ti.addColumn(col);
    }

    private ColumnInfo getWrappedIdCol(UserSchema us, AbstractTableInfo ds, String colName, String queryName)
    {
        WrappedColumn col = new WrappedColumn(ds.getColumn("patientId"), colName);
        col.setReadOnly(true);
        col.setIsUnselectable(true);
        col.setUserEditable(false);
        col.setFk(new QueryForeignKey(us, null, queryName, "patientId", "patientId"));

        return col;
    }
}

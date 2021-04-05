package com.example.demo.enums.handler;

import com.example.demo.enums.CupSize;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CupSizeTypeHandler  implements TypeHandler <CupSize>{

    @Override
    public void setParameter(PreparedStatement ps, int i, CupSize cupSize, JdbcType jdbcType) throws SQLException {
        ps.setString(i, cupSize.getValue());
    }

    @Override
    public CupSize getResult(ResultSet rs, String columnName) throws SQLException {
        return CupSize.size(rs.getString(columnName));
    }

    @Override
    public CupSize getResult(ResultSet rs, int columnIndex)throws SQLException {
        return CupSize.size(rs.getString(columnIndex));
    }

    @Override
    public CupSize getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return CupSize.size(cs.getString(columnIndex));
    }
}

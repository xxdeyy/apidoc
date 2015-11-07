package com.f2boy.typehandler;

import com.f2boy.JsonUtils;
import com.f2boy.domain.jsondo.ApiParams;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

/**
 * 处理对象：api表（接口）的入参列表
 */
public class ApiParamsJsonTypeHandler extends BaseTypeHandler<ApiParams> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ApiParams parameter, JdbcType jdbcType)
            throws SQLException {

        ps.setString(i, JsonUtils.object2Json(parameter));
    }

    @Override
    public ApiParams getNullableResult(ResultSet rs, String columnName) throws SQLException {

        ApiParams apiParams = JsonUtils.json2Object(rs.getString(columnName), ApiParams.class);
        if (apiParams != null) {
            Collections.sort(apiParams);
        }

        return apiParams;
    }

    @Override
    public ApiParams getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        ApiParams apiParams = JsonUtils.json2Object(rs.getString(columnIndex), ApiParams.class);
        if (apiParams != null) {
            Collections.sort(apiParams);
        }

        return apiParams;
    }

    @Override
    public ApiParams getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        ApiParams apiParams = JsonUtils.json2Object(cs.getString(columnIndex), ApiParams.class);
        if (apiParams != null) {
            Collections.sort(apiParams);
        }

        return apiParams;
    }
}

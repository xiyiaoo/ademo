/**
 * Copyright © Foresee Science & Technology Ltd. 
 */
package com.xiyiaoo.mybatis.typeHandler;

import com.xiyiaoo.constants.CodeEnum;
import com.xiyiaoo.constants.Gender;
import com.xiyiaoo.constants.ResourceType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: xiyiaoo@gmail.com
 * Date: 15-5-5 下午4:19
 * 枚举与数据库字段的转换器
 */
@MappedTypes({
        Gender.class,
        ResourceType.class
})
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum<E>> extends BaseTypeHandler<E> {
    private E[] codes;
    public CodeEnumTypeHandler(Class<E> type) {
        codes = type.getEnumConstants();
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int index, E e, JdbcType jdbcType) throws SQLException {
        //BaseTypeHandler保证了e不为空
        preparedStatement.setString(index, e.getCode());
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        if(resultSet.wasNull()){
            return null;
        }
        return parse(resultSet.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        if(resultSet.wasNull()){
            return null;
        }
        return parse(resultSet.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        if (callableStatement.wasNull()){
            return null;
        }
        return parse(callableStatement.getString(columnIndex));
    }

    private E parse(String code){
        for (E e : codes) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

}

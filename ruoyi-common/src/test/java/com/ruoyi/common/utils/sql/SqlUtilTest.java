package com.ruoyi.common.utils.sql;

import static org.junit.Assert.assertThrows;
import org.junit.Test;
import com.ruoyi.common.exception.UtilException;

public class SqlUtilTest
{
    @Test
    public void filterKeywordShouldRejectKeywordsSeparatedByWhitespace()
    {
        assertKeywordRejected("select 1");
        assertKeywordRejected("s e l e c t 1");
        assertKeywordRejected("select\u000B1");
    }

    @Test
    public void filterKeywordShouldAllowIdentifiersContainingKeywords()
    {
        SqlUtil.filterKeyword("CREATE TABLE select1 (id bigint)");
        SqlUtil.filterKeyword("CREATE TABLE selection (id bigint)");
    }

    private void assertKeywordRejected(String value)
    {
        assertThrows(UtilException.class, () -> SqlUtil.filterKeyword(value));
    }
}

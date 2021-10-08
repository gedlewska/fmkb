package com.example.fmkb

import java.util.regex.Matcher
import java.util.regex.Pattern

class UrlUtils {

    companion object {

        fun getUrl(string: String): String {
            val index = findUrl(string)
            if (index[0] != -1) {
                return string.substring(index[0], index[1])
            }
            return ""
        }

        fun getDescription(description: String): String {
            val index = findUrl(description)
            if (index[0] != -1) {
                return description.removeRange(index[0], index[1])
            }
            return description
        }

        private fun findUrl(string: String): Array<Int> {
            val matcher: Matcher = urlPattern.matcher(string)
            while (matcher.find()) {
                return arrayOf(matcher.start(1), matcher.end())
            }
            return arrayOf(-1, -1)
        }

        private val urlPattern: Pattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
        )
    }

}
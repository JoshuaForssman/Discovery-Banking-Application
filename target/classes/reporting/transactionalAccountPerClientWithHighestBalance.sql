SELECT DISTINCT ON(CL.CLIENT_ID)  CL.CLIENT_ID, CL.SURNAME, CA.CLIENT_ACCOUNT_NUMBER, AT.DESCRIPTION, CA.DISPLAY_BALANCE FROM CLIENT_ACCOUNT CA INNER JOIN CLIENT CL on CA.CLIENT_ID =  CL.CLIENT_ID INNER JOIN ACCOUNT_TYPE AT on CA.ACCOUNT_TYPE_CODE = AT.ACCOUNT_TYPE_CODE WHERE AT.TRANSACTIONAL = 1 AND (SELECT MAX(CA.DISPLAY_BALANCE) FROM CLIENT_ACCOUNT  GROUP BY CA.CLIENT_ID) GROUP BY CL.CLIENT_ID ,CA.CLIENT_ACCOUNT_NUMBER , CL.SURNAME ORDER BY CA.DISPLAY_BALANCE DESC

SELECT CONCAT(CL.TITLE , ' ' ,CL.NAME , ' ' ,CL.SURNAME) AS CLIENT, SUM(CASE WHEN(CA.ACCOUNT_TYPE_CODE = 'HLOAN') THEN(ROUND(CA.DISPLAY_BALANCE, 2))WHEN(CA.ACCOUNT_TYPE_CODE = 'PLOAN' )THEN(ROUND(CA.DISPLAY_BALANCE, 2)) END) AS LOAN_BALANCE, SUM(CASE WHEN(AT.TRANSACTIONAL = 1)THEN(ROUND(CA.DISPLAY_BALANCE, 2))END) AS TRANSACTIONAL_BALANCE,SUM(CASE WHEN(CCR.CONVERSION_INDICATOR = '/')THEN(ROUND(CA.DISPLAY_BALANCE / CCR.RATE,2)) WHEN(CCR.CONVERSION_INDICATOR = '*')THEN(ROUND(CA.DISPLAY_BALANCE * CCR.RATE, 2))END) AS NET_VALUE FROM CLIENT_ACCOUNT CA INNER JOIN CLIENT CL on CA.CLIENT_ID = CL.CLIENT_ID INNER JOIN CURRENCY_CONVERSION_RATE CCR on CA.CURRENCY_CODE = CCR.CURRENCY_CODE INNER JOIN ACCOUNT_TYPE AT ON CA.ACCOUNT_TYPE_CODE = AT.ACCOUNT_TYPE_CODE GROUP BY CL.CLIENT_ID
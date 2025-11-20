package com.tvm.internal.tvm_internal_project.response;

public class EmailContent {
    public static String generateSalaryMail(String employeeName, String month, Double netPay, String accountNumber) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Salary Credited Notification</title>
                    <style>
                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            background-color: #f6f8fb;
                            margin: 0;
                            padding: 0;
                        }
                        .email-wrapper {
                            max-width: 650px;
                            background-color: #ffffff;
                            margin: 40px auto;
                            border-radius: 10px;
                            overflow: hidden;
                            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                        }
                        .header {
                            background-color: #004aad;
                            color: white;
                            text-align: center;
                            padding: 20px;
                        }
                        .header img {
                            height: 60px;
                            margin-bottom: 10px;
                        }
                        .content {
                            padding: 30px;
                            color: #333333;
                            line-height: 1.7;
                        }
                        .content h2 {
                            color: #004aad;
                            font-size: 22px;
                            margin-bottom: 15px;
                        }
                        .salary-summary {
                            background-color: #f1f5ff;
                            padding: 15px 20px;
                            border-radius: 6px;
                            margin-top: 20px;
                            border: 1px solid #d3e0ff;
                        }
                        .salary-summary p {
                            margin: 5px 0;
                            font-size: 15px;
                        }
                        .salary-summary strong {
                            color: #004aad;
                        }
                        .footer {
                            background-color: #f2f4f8;
                            color: #777;
                            text-align: center;
                            padding: 15px;
                            font-size: 12px;
                        }
                        .footer a {
                            color: #004aad;
                            text-decoration: none;
                            font-weight: bold;
                        }
                    </style>
                </head>
                <body>
                    <div class="email-wrapper">
                        <div class="header">
                            <img src="cid:companyLogo" alt="Company Logo">
                            <h1>Salary Credited</h1>
                        </div>
                        <div class="content">
                            <h2>Dear %s,</h2>
                            <p>We are pleased to inform you that your salary for the month of <strong>%s</strong> has been successfully credited to your bank account.</p>               
                            <div class="salary-summary">
                                <p><strong>Account Number:</strong> %s</p>
                                <p><strong>Net Pay Amount:</strong> ₹%.2f</p>
                            </div>                
                            <p>The detailed payslip for the above period has been attached to this email for your reference.</p>                
                            <p>Thank you for your continued contributions and dedication to our organization.</p>                 
                            <p>Warm regards,<br>
                            <strong>HR Department</strong><br>
                            <em>Your Company Pvt. Ltd.</em></p>
                        </div>
                        <div class="footer">
                            <p>© 2025 Your Company Pvt. Ltd. All rights reserved.</p>
                            <p><a href="https://www.yourcompany.com">www.yourcompany.com</a></p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(employeeName, month, accountNumber, netPay);
    }
}

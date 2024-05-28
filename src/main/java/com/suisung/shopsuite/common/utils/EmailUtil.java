package com.suisung.shopsuite.common.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.suisung.shopsuite.common.pojo.dto.EmailDto;

public class EmailUtil {

    public static boolean send(EmailDto emailDto) {
        MailAccount mailAccount = getMailAccount(emailDto);
        String send = MailUtil.send(mailAccount, emailDto.getEmailToAddress(), emailDto.getSubject(), emailDto.getContent(), false);
        return true;
    }

    /**
     * 获取邮件配置对象
     *
     * @return
     */
    private static MailAccount getMailAccount(EmailDto emailDto) {
        MailAccount mailAccount = new MailAccount();

        mailAccount.setFrom(emailDto.getEmailAddr());
        mailAccount.setHost(emailDto.getEmailHost());
        mailAccount.setPort(emailDto.getEmailPort());
        mailAccount.setUser(emailDto.getEmailId());
        mailAccount.setPass(emailDto.getEmailPass());
        mailAccount.setAuth(true);

        // 使用SSL安全连接
        String email_secure = emailDto.getEmailSecure();
        switch (email_secure) {
            case "2":
                mailAccount.setSslEnable(true); // 使用SSL安全连接
                break;
            case "3":
                mailAccount.setStarttlsEnable(true); // 使用 STARTTLS安全连接
                break;
            default:
                break;
        }

        return mailAccount;
    }

}
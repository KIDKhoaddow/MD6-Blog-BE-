package com.team.case6.core.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$",
            message = "Tên người dùng bao gồm các ký tự chữ và số (A-ZA-Z0-9), chữ thường hoặc chữ hoa.\n" +
            "Tên người dùng cho phép của dấu chấm (.), Dấu gạch dưới (_) và dấu gạch nối (-).\n" +
            "Dấu chấm (.), Dấu gạch dưới (_) hoặc dấu gạch nối (-) không được là ký tự đầu tiên hoặc cuối cùng.\n" +
            "Dấu chấm (.), Undercore (_) hoặc dấu gạch nối (-) không xuất hiện liên tiếp, ví dụ, java..Regex\n" +
            "Số lượng ký tự phải từ 5 đến 20. ")
    @Length(min = 5, max = 20, message = "số lượng ký tự phải từ 5-20 ")
    @NotBlank(message = "Trường này không được để trống")
    private String username;

    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$",
            message = "chữ hoa và chữ thường Latin Latin A đến Z và A đến Z\n" +
                    "chữ số 0 đến 9\n" +
                    "Cho phép chấm (.), Dấu gạch dưới (_) và dấu gạch nối (-)\n" +
                    "dot (.) không phải là nhân vật đầu tiên hoặc cuối cùng\n" +
                    "DOT (.) Không xuất hiện liên tiếp, ví dụ: mkyong..yong@example.com không được phép\n" +
                    "Tối đa 64 ký tự")
    @NotBlank(message = "Trường này không được để trống")
    private String email;

    @Pattern(regexp = ".*[0-9]",
            message = "Mật khẩu phải chứa ít nhất một chữ số [0-9].")
    @Pattern(regexp = ".*[a-z]",
            message =     "Mật khẩu phải chứa ít nhất một ký tự Latin thường [a-z]." )
    @Pattern(regexp = ".*[A-Z]",
            message =  "Mật khẩu phải chứa ít nhất một ký tự tiếng Latin chữ hoa [A-Z].")
    @Pattern(regexp = ".*[!@#&()–[{}]:;',?/*~$^+=<>]",
            message = "Mật khẩu phải chứa ít nhất một ký tự đặc biệt như! @ # & ().\n")
    @NotBlank(message = "Trường này không được để trống")
    @Min(value = 8,message = "Mật khẩu phải chứa chiều dài ít nhất 8 ký tự ")
    @Max(value =20 ,message = "Mật khẩu phải chứa chiều dài tối đa 20 ký tự ")
    private String password;


    @Pattern(regexp = ".*[0-9]",
            message = "Mật khẩu phải chứa ít nhất một chữ số [0-9].")
    @Pattern(regexp = ".*[a-z]",
            message =     "Mật khẩu phải chứa ít nhất một ký tự Latin thường [a-z]." )
    @Pattern(regexp = ".*[A-Z]",
            message =  "Mật khẩu phải chứa ít nhất một ký tự tiếng Latin chữ hoa [A-Z].")
    @Pattern(regexp = ".*[!@#&()–[{}]:;',?/*~$^+=<>]",
            message = "Mật khẩu phải chứa ít nhất một ký tự đặc biệt như! @ # & ().\n")
    @NotBlank(message = "Trường này không được để trống")
    @Min(value = 8,message = "Mật khẩu phải chứa chiều dài ít nhất 8 ký tự ")
    @Max(value =20 ,message = "Mật khẩu phải chứa chiều dài tối đa 20 ký tự ")
    private String re_password;
}

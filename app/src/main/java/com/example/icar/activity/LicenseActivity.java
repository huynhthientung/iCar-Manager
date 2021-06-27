package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.icar.R;

public class LicenseActivity extends AppCompatActivity {

    private TextView txtPara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtPara = findViewById(R.id.textViewLicense);
        String para = "<b> Công ty cổ phần mạng trực tuyến ICar thực hiện dịch vụ bán hàng & thu tiền tại nhà trên toàn lãnh thổ Việt Nam. </b>\n" +
                "\n" +
                "- Quý khách vui lòng kiểm tra thật kỹ hàng hoá, đối chiếu sản phẩm với chứng từ, phiếu bảo hành (nếu có) trước khi nhận. Trường hợp Quý khách nhờ người thân nhận hàng, thì vẫn cần kiểm tra hàng kỹ trước khi nhận. Sau khi đã giao hàng thành công, ICar chỉ chịu trách nhiệm nếu xảy ra lỗi kỹ thuật do Nhà sản xuất (theo quy định Đổi/Trả hàng và Quy định Bảo hành), mọi trường hợp khác sẽ không thuộc trách nhiệm của chúng tôi.\n" +
                "\n" +
                "- Quý khách lưu ý: Tất cả các sản phẩm ICar bán ra đều có đầy đủ chứng từ như: Hóa đơn bán hàng; hoặc (và) Phiếu giao hàng; hoặc (và) Biên bản giao hàng; hoặc (và) Hóa đơn tài chính (nếu khách hàng yêu cầu). Do vậy khách hàng có quyền từ chối nhận hàng khi không có 1 trong 4 loại chứng từ trên, việc này nhằm đảm bảo chất lượng sản phẩm, mua đúng hàng của ICar, đồng thời đảm bảo quyền lợi của Quý khách trong việc Đổi/Trả hàng.\n" +
                "\n" +
                "- Thời gian giao hàng có thể chậm hơn dự kiến vì một số lý do như: Địa chỉ khách hàng không đúng, Khách hàng không có ở nhà, Nhân viên giao hàng không liên hệ được với khách hàng, thiên tai, hỏa hoạn,... Nếu vì lý do của ICar, chúng tôi sẽ liên hệ với Quý khách để sắp xếp lại thời gian giao hàng hợp lý.\n" +
                "\n" +
                "- Trường hợp đã quá số thời gian dự kiến mà khách hàng chưa nhận được hàng, vui lòng phản hồi lại để chúng tôi có biện pháp khắc phục nhanh nhất. Trong thời gian chờ hàng nếu Quý khách muốn thay đổi đơn hàng (Thay đổi sản phẩm, Không muốn nhận hàng nữa,...) mà bên dịch vụ chưa phát khách hàng, vui lòng thông báo lại ICar để chúng tôi giải quyết với bên dịch vụ chuyển phát.";
        para = para.replace("\n", "<br />");
        txtPara.setText(Html.fromHtml(para));
        txtPara.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
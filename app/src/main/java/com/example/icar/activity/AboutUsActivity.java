package com.example.icar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.icar.R;

public class AboutUsActivity extends AppCompatActivity {

    private TextView txtAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtAboutUs = findViewById(R.id.textView_AboutUs);

        String para = "<b> Chúng tôi là ai ? </b> \n "
                + "\nICar là ứng dụng đầu tiên ở Châu Á cung cấp dịch vụ vận chuyển hàng hóa chuyên nghiệp thông qua sử dụng nền tảng công nghệ đột phá. ICar kết nối trực tiếp các cá nhân, doanh nghiệp với hàng ngàn đối tác tài xế theo thời gian thực để đáp ứng mọi nhu cầu vận chuyển. Có thể nói, Icar đã mang đến một khái niệm mới về trải nghiệm sử dụng dịch vụ vận chuyển hàng ngày của khách hàng thông qua việc cung cấp dịch vụ tiện lợi và hiệu quả. Từ lúc thành lập đến nay, ICar đã mở rộng phạm vi hoạt động từ Hong Kong đến Singapore, Đài Loan, Trung Quốc, Hàn Quốc, Ấn Độ và Việt Nam. Chúng tôi đã tăng sự hiện diện của mình ở hơn 300 thành phố khắp Châu Á với hơn 8 triệu đối tác tài xế tham gia vào nền tảng vận chuyển. \n "
                + "\n<b> Chúng tôi đã bắt đầu như thế nào?  </b> \n "
                + "\nHành trình của chúng tôi bắt đầu vào năm 2013 tại Hồng Kông. Những người đồng sáng lập ICar đã khởi nghiệp với việc giao các phần cơm trưa bằng xe van thông qua tổng đài cho đến một ngày nọ, họ có nhu cầu cấp thiết để xử lý hàng ngàn đơn đặt hàng liên tục. Sự kiện quan trọng này đã khiến cho họ nghĩ ra ý tưởng tập hợp các tài xế xe van thành một nhóm trò chuyện nơi mà các tài xế có thể đáp ứng các đơn đặt hàng ngay lập tức như một giải pháp cho mô hình kinh doanh trung tâm cuộc gọi manh mún và kém hiệu quả. Tuy nhiên, nó vẫn giới hạn khả năng để mở rộng kinh doanh. Sau đó, họ tập trung vào công nghệ và ICar đã ra đời. \n "
                + "\n<b> Tại sao chọn ICar ? </b> \n "
                + "\nKể từ khi ra mắt, chúng tôi đã mở rộng danh mục dịch vụ của mình để cung cấp giải pháp giao hàng nhanh chóng và linh hoạt trên nhiều nền tảng khác nhau tại các thị trường mà chúng tôi đang hoạt động. ICar luôn sẵn sàng phá vỡ mọi giới hạn để có những bước tiến đột phá nhằm mang đến cho quý khách hàng một giải pháp giao nhận toàn diện, giúp bạn giải quyết mọi khó khăn trong khâu vận tải cùng những trải nghiệm dịch vụ tuyệt vời. \n "
                + "\n<b> Liên hệ với chúng tôi </b> \n "
                + "<b> Website: </b> www.icar.com \n "
                + "<b> Địa chỉ: </b> 268 Tô Hiến Thành, Phường 15, Quận 10, Thành phố Hồ Chí Minh  \n "
                + "<b> Email: </b> icarcompany@icar.com \n ";
        para = para.replace("\n", "<br />");
        txtAboutUs.setText(Html.fromHtml(para));
        txtAboutUs.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
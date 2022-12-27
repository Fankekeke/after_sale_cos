package cc.mrbird.febs.cos.entity.vo;

import cc.mrbird.febs.cos.entity.DeliveryReview;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class DeliveryReviewVo extends DeliveryReview {

    private String city;

    private String area;

    private String province;

    private String rentUserAvatar;

    private String ownerUserName;

    private String ownerUserAvatar;

    private String communityName;

    private String houseAddress;

    private boolean deliveryOver;

    private boolean rentOver;

}

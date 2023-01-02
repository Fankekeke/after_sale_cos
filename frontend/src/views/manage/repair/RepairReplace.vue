<template>
  <a-modal v-model="show" title="更换维修人员" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="submit" type="primary">
        确认
      </a-button>
      <a-button @click="onClose">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="repairData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="repairData !== null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">维修信息</span></a-col>
        <a-col :span="8"><b>维修人员：</b>
          {{ repairData.staffName }}
        </a-col>
        <a-col :span="8"><b>产品名称：</b>
          {{ repairData.productName }}
        </a-col>
        <a-col :span="8"><b>产品类型：</b>
          <span v-if="repairData.productType == 1">标准件</span>
          <span v-if="repairData.productType == 2">工序外包</span>
          <span v-if="repairData.productType == 3">工序外购</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>维修状态：</b>
          <span v-if="repairData.repairStatus == 0">待接收</span>
          <span v-if="repairData.repairStatus == 1">正在检测问题</span>
          <span v-if="repairData.repairStatus == 2">维修中</span>
          <span v-if="repairData.repairStatus == 3">维修完成</span>
          <span v-if="repairData.repairStatus == 4">异常退回</span>
        </a-col>
      </a-row>
      <br>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="orderInfo !== null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">工单信息</span></a-col>
        <a-col :span="8"><b>工单编号：</b>
          {{ orderInfo.orderCode }}
        </a-col>
        <a-col :span="8"><b>客户名称：</b>
          {{ orderInfo.userName }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ orderInfo.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="orderInfo !== null">
        <a-col :span="8"><b>当前状态：</b>
          <span v-if="orderInfo.status == 0">正在对应</span>
          <span v-if="orderInfo.status == 1">已派发</span>
          <span v-if="orderInfo.status == 2">缴费</span>
          <span v-if="orderInfo.status == 3">正在维修</span>
          <span v-if="orderInfo.status == 4">维修完成</span>
          <span v-if="orderInfo.status == 5">已退换</span>
          <span v-if="orderInfo.status == 6">完成</span>
        </a-col>
        <a-col :span="8"><b>服务类型：</b>
          {{ orderInfo.serverTypeName }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ orderInfo.createDate }}
        </a-col>
      </a-row>
      <br>
      <br>
      <br>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">选择维修人员</span></a-col>
      </a-row>
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="50">
        <a-col :span="24" style="margin-bottom: 15px">
          <a-radio-group v-model="staffId" button-style="solid">
            <a-radio-button v-for="(item, index) in staffList" :key="index" :value="item.id">
              {{ item.name }}（{{ item.workStatus > 0 ? '工作' : '空闲' }}）
            </a-radio-button>
          </a-radio-group>
        </a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'RepairReplace',
  props: {
    repairShow: {
      type: Boolean,
      default: false
    },
    repairData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.repairShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      loading: false,
      staffList: [],
      staffId: null,
      orderInfo: null
    }
  },
  watch: {
    'repairShow': function (value) {
      if (value) {
        this.selectStaffByProduct(this.repairData.productId)
        this.selectOrder(this.repairData.repairCode)
      }
    }
  },
  methods: {
    moment,
    selectStaffByProduct (productId) {
      this.$get(`/cos/staff-info/work/${productId}`).then((r) => {
        this.staffList = r.data.data
      })
    },
    selectOrder (repairCode) {
      this.$get(`/cos/order-info/detail/repair/${repairCode}`).then((r) => {
        this.orderInfo = r.data
      })
    },
    submit () {
      if (this.staffId === null) {
        this.$message.error('请选择维修人员')
        return false
      }
      this.$get(`/cos/order-info/bind/repair`, {
        'orderCode': this.orderInfo.orderCode,
        'staffId': this.staffId
      }).then((r) => {
        this.$emit('success')
      })
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>

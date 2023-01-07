<template>
  <a-modal v-model="show" title="维修处理" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="s" @click="handleAudit(-1)" type="primary">
        下一步提交
      </a-button>
      <a-button key="x" @click="handleAudit(4)" type="danger">
        异常退回
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="repairData !== null">
      <div style="padding-left: 24px;padding-right: 24px;margin-bottom: 50px;margin-top: 50px">
        <a-steps :current="current" progress-dot size="small">
          <a-step title="待接收" />
          <a-step title="正在检测问题" />
          <a-step title="维修中" />
          <a-step title="维修完成" />
          <a-step title="异常退回" />
        </a-steps>
      </div>
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
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="orderInfo != null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">工单信息</span></a-col>
        <a-col :span="8"><b>工单名称：</b>
          {{ orderInfo.orderName }}
        </a-col>
        <a-col :span="8"><b>工单编号：</b>
          {{ orderInfo.orderCode }}
        </a-col>
        <a-col :span="8"><b>客户名称：</b>
          {{ orderInfo.userName }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="orderInfo != null">
        <a-col :span="8"><b>联系方式：</b>
          {{ orderInfo.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="orderInfo != null">
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
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">备注信息</span></a-col>
        <a-col :span="24">
          {{ repairData.remark }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">工单图片</span></a-col>
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </a-col>
      </a-row>
      <br/>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'repairView',
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
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      reserveInfo: null,
      orderInfo: null,
      current: 0
    }
  },
  watch: {
    repairShow: function (value) {
      if (value) {
        this.dataInit(this.repairData)
        this.current = this.repairData.repairStatus
      }
    }
  },
  methods: {
    dataInit (repairData) {
      // 工单信息
      if (repairData.repairCode !== null && repairData.repairCode !== '') {
        this.$get(`/cos/order-info/detail/repair/${repairData.repairCode}`).then((r) => {
          this.orderInfo = r.data
          if (this.orderInfo.images !== null && this.orderInfo.images !== '') {
            this.imagesInit(this.orderInfo.images)
          }
          // 预约信息
          this.$get(`/cos/reserve-info/detail/${this.orderInfo.orderCode}`).then((r) => {
            this.reserveInfo = r.data.data
          })
        })
      }
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    handleAudit (status) {
      if (status === -1) {
        status = Number(this.repairData.repairStatus) + 1
      }
      this.$get(`/cos/repair-info/edit/status`, { repairId: this.repairData.id, status }).then((r) => {
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

<template>
  <a-modal v-model="show" title="订单评价" @cancel="onClose" :width="600">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='准时得分' v-bind="formItemLayout">
            <a-input-number :min="1" :max="100" style="width: 100%" v-decorator="[
              'scheduleScore',
               { rules: [{ required: true, message: '请输入准时得分!' }] }
              ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='维修得分' v-bind="formItemLayout">
            <a-input-number :min="1" :max="100" style="width: 100%" v-decorator="[
              'repairScore',
               { rules: [{ required: true, message: '请输入维修得分!' }] }
              ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='服务得分' v-bind="formItemLayout">
            <a-input-number :min="1" :max="100" style="width: 100%" v-decorator="[
              'serviceScore',
               { rules: [{ required: true, message: '请输入服务得分!' }] }
              ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'OrderEvaluate',
  props: {
    orderAddVisiable: {
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.orderAddVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      serviceSortList: [],
      productList: []
    }
  },
  mounted () {
    this.getServiceSort()
    this.getProduct()
  },
  methods: {
    getServiceSort () {
      this.$get(`/cos/service-sort/list`).then((r) => {
        this.serviceSortList = r.data.data
      })
    },
    getProduct () {
      this.$get(`/cos/product-info/list`).then((r) => {
        this.productList = r.data.data
      })
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
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        values.userId = this.orderData.customerId
        values.orderCode = this.orderData.orderCode
        values.staffId = this.orderData.staffId
        if (!err) {
          this.loading = true
          this.$post('/cos/staff-evaluation', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>

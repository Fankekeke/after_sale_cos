<template>
  <a-card :bordered="false" class="card-area">
    <a-row :gutter="25">
      <a-col :span="17">
        <div style="background:#ECECEC; padding:30px;margin-top: 30px">
          <a-row :gutter="25">
            <a-col :span="8" v-for="(item, index) in evaluateList" :key="index">
              <a-card :bordered="false">
                <span slot="title">
                  <span style="font-size: 14px;font-family: SimHei">
                    {{ item.staff.name }}
                    <span>【{{ item.staff.code }}】</span>
                  </span>
                </span>
                <p>card content</p>
              </a-card>
            </a-col>
          </a-row>
        </div>
      </a-col>
      <a-col :span="7">
        <a-col :span="24">
          <apexchart type="radar" height="350" :options="chartOptions" :series="series"></apexchart>
        </a-col>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
export default {
  name: 'Evaluate',
  data () {
    return {
      evaluateList: [],
      series: [{
        name: '得分',
        data: [80, 50, 30, 40]
      }],
      chartOptions: {
        chart: {
          height: 380,
          type: 'radar'
        },
        title: {
          text: '员工评价得分'
        },
        xaxis: {
          categories: ['准时得分', '维修得分', '服务得分', '综合得分']
        }
      }
    }
  },
  mounted () {
    this.getEvaluateList()
  },
  methods: {
    getEvaluateList () {
      this.$get(`/cos/staff-evaluation/staff/evaluate`).then((r) => {
        this.evaluateList = r.data.data
        console.log(JSON.stringify(this.evaluateList))
      })
    }
  }
}
</script>

<style scoped>
</style>

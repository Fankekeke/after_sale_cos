<template>
  <a-card :bordered="false" class="card-area">
    <a-row :gutter="25">
      <a-col :span="17">
        <div style="background:#ECECEC; padding:30px;margin-top: 30px">
          <a-row :gutter="25">
            <a-col :span="8" v-for="(item, index) in evaluateList" :key="index">
              <a-card :bordered="false" hoverable @click="checkEvaluate(item)">
                <span slot="title">
                  <span style="font-size: 14px;font-family: SimHei">
                    {{ item.staff.name }}
                    <span>【{{ item.staff.code }}】</span>
                  </span>
                </span>
                <div style="font-size: 14px;font-family: SimHei;padding: 0px 20px">
                  <a-row :gutter="15">
                    <a-col :span="12" style="margin-bottom: 5px">准时得分: {{item.scheduleScore}}</a-col>
                    <a-col :span="12" style="margin-bottom: 5px">维修得分: {{item.repairScore}}</a-col>
                    <a-col :span="12" style="margin-bottom: 5px">服务得分: {{item.serviceScore}}</a-col>
                  </a-row>
                </div>
              </a-card>
            </a-col>
          </a-row>
        </div>
      </a-col>
      <a-col :span="7">
        <a-col :span="24" style="margin-top: 30px">
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
        data: []
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
    checkEvaluate (score) {
      let data = [score.scheduleScore, score.repairScore, score.serviceScore, score.score, 100]
      this.series[0].data = data
      this.chartOptions.title.text = score.staff.name + '评价得分'
    },
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

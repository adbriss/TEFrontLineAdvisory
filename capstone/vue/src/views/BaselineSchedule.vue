<template>
  <v-app>
    <v-container >
      <v-form ref="form" lazy-validation>
        <v-text-field
            v-model="project.projectName"
            label="Project Name - From Project Creation"
            required
            :readonly="true"
        ></v-text-field>

        <v-text-field
            label="Contract - From Project Creation"
            type="text"
            v-model="name"
            :readonly="true"
            required
        ></v-text-field>

        <v-text-field
            v-model="project.dateReceived"
            type="date"
            label="Date Received - From Project Creation"
            required
            :readonly="true"
        ></v-text-field>

        <v-text-field
            v-model="project.budget"
            type="number"
            label="Budget - From Project Creation"
            placeholder="$"
            required
            :readonly="true"
        ></v-text-field>

      </v-form>
    </v-container>
    <v-container class="check-container">
          <v-checkbox
              label="Design" value="Design"
              @click="updateBaseline"
              v-model="phase">
          </v-checkbox>
          <v-checkbox
              label="Pre Construction" value="Pre-Construction"
              @click="updateBaseline"
              v-model="phase">
          </v-checkbox>
          <v-checkbox
              label="Construction" value="Construction"
              @click="updateBaseline"
              v-model="phase">
          </v-checkbox>
        </v-container>
          <h2 class="text-center">{{phase}}</h2>
        <BaselineSchedEntry :baselineItems="filteredBaseline"/>
    <div class="text-center">
      <router-link class="text-decoration-none" :to="{path: '/home'}">
        <v-btn class="button"
              color=#8c090e
              elevation="2"
              outlined
        >Home
        </v-btn>
      </router-link>
    </div>
  </v-app>
</template>

<script>
import ScheduleService from '../services/ScheduleService.js'
import ProjectService from '../services/ProjectService.js'
import BaselineSchedEntry from '../components/BaselineSchedEntry.vue'

export default {
  components: {BaselineSchedEntry},
  data()
  {
    return {
      addProjectSuccess: false,
      addProjectSuccessMessage: "Successfully Added Project!",
      addProjectFailure: false,
      addProjectFailureMessage: "Something Went Wrong! Please Try again...",
      contract: '',
      name: '',
      phase: '',
      phaseDesign: false,
      phasePreConstruction: false,
      phaseConstruction: false,
      project: this.$store.state.project,
      baselineItems: [],
      filteredBaseline: [],
    };
  },
  methods: {
    allDone(){
      alert('Changes Saved')
    },
    updateBaseline(){
      this.filteredBaseline = this.baselineItems.filter((each) => {
        return this.phase == each.phaseDescription;
      })
    }
  },
  created(){
        const baseID = this.$route.params.id
        ScheduleService.listBaselineItems(baseID).then((response) =>{
            if (response.status == 200 || response.status == 201){
                this.baselineItems = response.data;
            }
        })
        .catch((error) => {
          const response = error.response;
          if (response.status == 400){
            alert('baseline',error)
          }
        })
        ProjectService.getContracts().then((response) =>{
            this.contract = response.data.filter((each) => {
                 return each.id == this.project.contractId
            })
            this.name = this.contract[0].contractName
        })
        .catch((error) => {
          const response = error.response;
          if (response.status == 400){
            alert('contract',response)
          }
        })

    },
};


</script>

<style>
.check-container{
    display: flex;
    flex-direction: row;

}
</style>
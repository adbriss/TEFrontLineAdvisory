<template>
  <v-app>
    <v-container>
      <br />
      <h2 class="text-center">Pending Modifications / Change Orders</h2>
      <br /><br />
      <v-simple-table v-show="!populated">
        <thead style="display:table-header-group;" >
        <tr>
          <th>Name</th>
          <th>Court Date</th>
          <th>Schedule Impacted?</th>
          <th>Budget Impacted?</th>
          <th>View Details</th>
        </tr>
        </thead>
        <tbody>
          <tr v-for="mod in filteredMods" :key="mod.id">
            <td>{{mod.modCoName}}</td>
            <td>{{mod.courtDate}}</td>
            <td>{{mod.scheduleImpacted}}</td>
            <td>{{mod.budgetImpacted}}</td>
            <td>
              <v-btn
              elevation="2"
              class="text-decoration-none"
              @click="viewModCo(mod)">
                View/Edit
              </v-btn>
            </td>
          </tr>
        </tbody>
      </v-simple-table><br><br>
      <h1 class="text-center" v-if="populated">No Current Pending Modifications/Change Orders</h1>
    </v-container>
    <div class="text-center">
    <br><br>
    <router-link
        class="text-decoration-none"
        :to="{ path: '/home' }">
      <v-btn class="button" color="#8c090e" elevation="2" outlined
      >Back</v-btn>
    </router-link>
    <router-link class="text-decoration-none" :to="{ path: '/home' }">
      <v-btn class="button" color="#8c090e" elevation="2" outlined
      >Home</v-btn>
    </router-link>
    </div>
  </v-app>
</template>

<script>
import ModCOService from '../services/ModCOService.js'

export default {
  components: {},
  data(){
    return {
      modList: [],
      filteredMods: [],
    }
  },
  methods: {
    viewModCo(mod){
      this.$router.push({name: 'ProcessModCO', params: {id: mod.id}})
    }
  },
  created(){
    ModCOService.listModCO().then((response) => {
      if (response.status == 200 || response.status == 201){
        this.modList = response.data;
      }
      this.filteredMods = this.modList.filter((each) => {
        return each.approveDateTm == null;
      })
    })
  },
  computed: {
    populated(){
      return this.filteredMods.length <= 0;
    }
  }

}
</script>

<style>

</style>
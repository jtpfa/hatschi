/**
 * Add the vuex persistence module to synchronize the vuex store with the localStorage
 *
 * @author Jonas Pfannkuche
 */

import VuexPersistence from 'vuex-persist'

export default ({ store }) => {
    new VuexPersistence({}).plugin(store)
}

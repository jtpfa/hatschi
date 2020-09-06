<template>
    <main>
        <h1 class="mb-5">Neues Produkt anlegen</h1>

        <product-add />
    </main>
</template>

<script>
/**
 * @component DashboardProductAddPage
 * @desc Page for adding new products
 * @author Jonas Pfannkuche
 */

import ProductAdd from '~/components/dashboard/products/add'

export default {
    components: { ProductAdd },
    layout: 'dashboard',
}
</script>

<style scoped></style>

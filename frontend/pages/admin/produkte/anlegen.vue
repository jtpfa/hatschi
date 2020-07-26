<template>
    <div>
        <h1 class="mb-5">Neues Produkt anlegen</h1>

        <product-add />
    </div>
</template>

<script>
import ProductAdd from '~/components/admin/products/add'

export default {
    components: { ProductAdd },
    layout: 'admin',
}
</script>

<style scoped></style>

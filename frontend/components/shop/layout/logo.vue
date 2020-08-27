<template>
    <b-navbar-brand class="d-flex justify-content-center align-items-center" href="/" title="Zur Startseite von PC-MR">
        <icon-logo aria-label="Logo von PC-MR" class="mr-3" />

        <span v-if="header" class="big-noodle name text-dark">PCMR</span>
        <span v-else class="big-noodle description text-light">
            Dein Online-
            <br />
            Shop Für
            <br />
            PC-Equipment
            <br />
        </span>
    </b-navbar-brand>
</template>

<script>
import IconLogo from '~/components/general/icons/logo'

export default {
    name: 'PageLogo',
    components: { IconLogo },
    props: {
        header: {
            type: Boolean,
            default: false,
        },
    },
}
</script>

<style lang="scss" scoped>
.navbar-brand {
    svg {
        width: 5rem;
        height: auto;
        transition: all 0.3s ease-in-out;
    }

    &:hover {
        svg {
            transform: scale(1.05);
        }
    }
}

.name {
    font-size: 3.25rem;
}

.description {
    font-size: 1.25rem;
    line-height: 1.25;
}
</style>

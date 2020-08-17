<template>
    <div class="d-flex justify-content-center align-items-center">
        <b-navbar-brand href="/" title="Zur Startseite von PC-MR">
            <icon-logo aria-label="Logo von PC-MR" />
        </b-navbar-brand>
        <span class="big-noodle description" :class="{ 'text-light': darkBackground, 'text-dark': !darkBackground }">
            Dein Online-
            <br />
            Shop Für
            <br />
            PC-Equipment
            <br />
        </span>
    </div>
</template>

<script>
import IconLogo from '~/components/icons/logo'

export default {
    name: 'PageLogo',
    components: { IconLogo },
    props: {
        darkBackground: {
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

.description {
    font-size: 1.25rem;
    line-height: 1.25;
}
</style>
